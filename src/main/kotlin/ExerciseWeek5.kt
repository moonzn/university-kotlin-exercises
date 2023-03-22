import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor

object Week5 {
    data class Student(
        val number: Int,
        val name: String,
        val type: StudentType? = null
    )

    enum class StudentType {
        Bachelor, Master, Doctoral
    }

    interface TypeMapping {
        fun mapType(kProperty: KProperty<*>): String
        fun mapObject(objekt: Any?): String
    }

    class SQLMapping : TypeMapping {
        override fun mapType(kProperty: KProperty<*>): String {
            return when (kProperty.returnType.classifier) {
                Int::class -> " INT"
                String::class -> " CHAR"
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
            val fields = clazz.dataClassFields.joinToString(transform = ::kPropertyParse)
            return "CREATE TABLE " + clazz.simpleName!!.uppercase() + " (" + fields + ");"
        }

        private fun kPropertyParse(kProperty: KProperty<*>): String {
            val nullable = if (!kProperty.returnType.isMarkedNullable) " NOT NULL" else ""
            return kProperty.name + typeMapping.mapType(kProperty) + nullable
        }

        fun insertInto(obj: Any): String {
            val fields =
                obj::class.dataClassFields.joinToString(prefix = " (", separator = ", ", postfix = ") ") { it.name }
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

    val <T : Any> KClass<T>.enumConstants: List<T>
        get() {
            require(isEnum) { "instance must be enum" }
            return java.enumConstants.toList()
        }
}