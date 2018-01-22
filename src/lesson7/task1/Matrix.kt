@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson7.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if (height <= 0 || width <= 0) throw IllegalArgumentException("createMatrix")
    val result = MatrixImpl<E>(height, width)
    for (i in 0 until height)
        for (j in 0 until width)
            result.set(i, j, e)
    return result
}

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int) : Matrix<E> {
    var list = mutableMapOf<Cell, E>()

    override fun get(row: Int, column: Int): E = list[Cell(row, column)] ?: throw IllegalArgumentException("get")

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        list[Cell(row, column)] = value
    }

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)

    override fun equals(other: Any?) = other is MatrixImpl<*> &&
            height == other.height &&
            width == other.width &&
            list == other.list
    
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append('[')
        for (i in 0 until height) {
            sb.append("[")
            for (j in 0 until width) {
                sb.append(list[Cell(i, j)].toString())
                sb.append(", ")
            }
            sb.append("], ")
        }
        sb.append("]")
        return sb.toString()
    }

    override fun hashCode(): Int {
        var result = 5
        result = result * 31 + width
        result = result * 31 + list.hashCode()
        return result
    }
}


