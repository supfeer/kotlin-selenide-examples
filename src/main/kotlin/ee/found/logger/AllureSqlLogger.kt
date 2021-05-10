package ee.found.logger

import com.github.vertical_blank.sqlformatter.SqlFormatter.standard
import io.qameta.allure.Allure.addAttachment
import org.jetbrains.exposed.sql.SqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.statements.StatementContext
import org.jetbrains.exposed.sql.statements.expandArgs

object AllureSqlLogger : SqlLogger {
    override fun log(context: StatementContext, transaction: Transaction) {
        addAttachment("Query", standard().format(context.expandArgs(transaction)))
    }
}
