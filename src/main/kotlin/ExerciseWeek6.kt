import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KProperty
import kotlin.reflect.full.*

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class DbName(val desc: String)
annotation class PrimaryKey
@Target(AnnotationTarget.PROPERTY)
annotation class Length(val value: Int)

@DbName("STUDENT")
data class Student(
    @PrimaryKey
    val number: Int,
    @Length(50)
    val name: String,
    @DbName("degree")
    val type: StudentType? = null
)

enum class StudentType {
    Bachelor, Master, Doctoral
}

interface TypeMapping {
    fun mapType(kProperty: KProperty<*>): String
    fun mapObject(objekt: Any?): String
}

class SQLMapping: TypeMapping {
    override fun mapType(kProperty: KProperty<*>): String {
        return when (kProperty.returnType.classifier) {
            Int::class -> " INT"
            String::class -> " CHAR" //TODO IF HAS ANNOTATED LENGTH VARCHAR + LENGTH
            else -> if (kProperty.returnType.classifier.isEnum) mapEnum(kProperty) else " NOT PREDICTED"
        }
    }

    private fun mapEnum(kProperty: KProperty<*>): String {
        val clazz = kProperty.returnType.classifier as KClass<*>
        return clazz.enumConstants.joinToString(prefix = " ENUM(´", separator = "´, ´", postfix = "´)")
    }

    override fun mapObject(objekt: Any?): String {
        if (objekt == null) return ""
        return objekt::class.dataClassFields.joinToString {
            when (it.returnType.classifier) {
                Int::class -> it.call(objekt).toString()
                else -> "´" + it.call(objekt).toString() + "´"
            }
        }
    }
}

class SQLGenerator(private val typeMapping: TypeMapping) {
    fun createTable(clazz: KClass<*>): String {
        var name = clazz.simpleName!!.uppercase()
        if (clazz.hasAnnotation<DbName>()) {
            name = clazz.findAnnotation<DbName>()!!.desc
        }
        val fields = clazz.dataClassFields.joinToString(transform = ::kPropertyParse)
        return "CREATE TABLE $name ($fields);"
    }

    private fun kPropertyParse(kProperty: KProperty<*>): String {
        var nullable = if (!kProperty.returnType.isMarkedNullable) " NOT NULL" else ""
        if (kProperty.hasAnnotation<PrimaryKey>()) {
            nullable = " PRIMARY KEY"
        }
        //TODO IF ANNOTATED PRIMARY KEY?
        return kProperty.name + typeMapping.mapType(kProperty) + nullable
    }

    fun insertInto(obj: Any): String {
        val fields = obj::class.dataClassFields.joinToString(prefix = " (", separator = ", ", postfix = ") ") { it.name }
        return "INSERT INTO " + obj::class.simpleName!!.uppercase() + fields + "VALUES (" + typeMapping.mapObject(obj) + ");"
    }
}

//Helper Values

val KClass<*>.dataClassFields: List<KProperty<*>>
    get() {
        require(isData) { "instance must be data class" }
        return primaryConstructor!!.parameters.map { p ->
            declaredMemberProperties.find { it.name == p.name }!!
        }
    }

val KClassifier?.isEnum: Boolean
    get() = this is KClass<*> && this.isSubclassOf(Enum::class)

val <T : Any> KClass<T>.enumConstants: List<T> get() {
    require(isEnum) { "instance must be enum" }
    return java.enumConstants.toList()
}