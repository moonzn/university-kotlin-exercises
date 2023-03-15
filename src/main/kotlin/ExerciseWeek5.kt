import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor

data class Student(
    val number: Int,
    val name: String,
    val type: StudentType? = null
)

enum class StudentType {
    Bachelor, Master, Doctoral
}

// obtem lista de atributos pela ordem do construtor primario
val KClass<*>.dataClassFields: List<KProperty<*>>
    get() {
        require(isData) { "instance must be data class" }
        return primaryConstructor!!.parameters.map { p ->
            declaredMemberProperties.find { it.name == p.name }!!
        }
    }

// saber se um KClassifier é um enumerado
val KClassifier?.isEnum: Boolean
    get() = this is KClass<*> && this.isSubclassOf(Enum::class)

// obter uma lista de constantes de um tipo enumerado
val <T : Any> KClass<T>.enumConstants: List<T> get() {
    require(isEnum) { "instance must be enum" }
    return java.enumConstants.toList()
}

fun createTable(clazz: KClass<*>): String {
    val start = "CREATE TABLE " + clazz.simpleName + " ("

    fun listOfParameters(): List<String> {
        val fields = clazz.dataClassFields
        val listOfParameters = mutableListOf<String>()
        fields.forEach { kProperty ->
            if (kProperty.returnType.classifier.isEnum) {
                val listOfEnums = mutableListOf<String>()
                (kProperty.returnType.classifier as KClass<*>).enumConstants.forEach { enumString ->
                    listOfEnums.add("´$enumString´")
                }
                val enum = listOfEnums.joinToString(prefix = "(", postfix = ")", separator = ", ")
                val parameter = kProperty.name + " ENUM" + enum
                listOfParameters.add(parameter)
            } else {
                var parameter = kProperty.name + " "
                parameter += if (kProperty.returnType.toString() == "kotlin.String") {
                    "CHAR"
                } else {
                    kProperty.returnType.toString().replace("kotlin.", "").uppercase()
                }
                if (!kProperty.returnType.isMarkedNullable) {
                    parameter += " NOT NULL"
                }
                listOfParameters.add(parameter)
            }
        }
        return listOfParameters
    }
    return listOfParameters().joinToString(prefix = start, postfix = ");", separator = ", ")
}

fun insertInto(clazz: KClass<*>): String {
    return ""
}

fun main() {
    val fields: List<KProperty<*>> = Student::class.dataClassFields
    println(fields)
    val isEnum = StudentType::class.isEnum
    println(isEnum)
    val enumConstants: List<StudentType> = StudentType::class.enumConstants
    println(enumConstants)
}