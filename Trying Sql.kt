
import java.sql.Connection
import java.sql.DriverManager
import java.time.LocalDateTime

class Time(){
    var date: String? = "";
    var Hour: Int? = null;
    var Minute: Int? = null;

    fun updateTime(){
        val cdt = LocalDateTime.now()
        date = "${cdt.year}-${cdt.monthValue}-${cdt.dayOfMonth}"
        Hour = cdt.hour
        Minute = cdt.minute
    }
    fun getdate():String? = date
    fun getHour():Int {return Hour!!}
    fun getMinute():Int {return Minute!!}
}

fun connectToData(): Connection {
    val url = "jdbc:sqlite:C:/Users/Administrator/IdeaProjects/Test/src/data.db"
    //Class.forName("org.sqlite.JDBC");
    val createTable = """
            CREATE TABLE IF NOT EXISTS TEST (
                date TEXT,
                loginTime TEXT,
                logoutTime TEXT
            )
        """
    val cn = DriverManager.getConnection(url)
    cn.createStatement().executeUpdate(createTable)
    cn.close()
    return DriverManager.getConnection(url)
}

fun insert( cn: Connection, Date:String,Hourin:Int,Minutein:Int,Hourout:Int,Minuteout:Int){
    val insetValue = """
        INSERT INTO Test (date, loginTime, logoutTime)
        VALUES ('$Date', '$Hourin:$Minuteout', '$Hourout:$Minuteout')
    """
    cn.createStatement().executeUpdate(insetValue)
}
fun insertUser(cn: Connection, userId: String?, userPass: String?){
    val dataIfNotExist = """
        CREATE TABLE IF NOT EXISTS userAccount(
                UserId TEXT,
                UserPass TEXT
            )
    """
    cn.createStatement().executeUpdate(dataIfNotExist)
    val dataFormat = """
        INSERT INTO userAccount (userId, userPass)
        VALUES ('$userId', '$userPass')
    """
    cn.createStatement().executeUpdate(dataFormat)
}
fun readdata(cn: Connection){

    val query = "SELECT * FROM Test"
    val statement = cn.createStatement()
    val resultSet = statement.executeQuery(query)

    while(resultSet.next()){
        val date = resultSet.getString("date")
        val loginTime = resultSet.getString("loginTime")
        val logoutTime = resultSet.getString("logoutTime")
        println("Date: $date, Login Time: $loginTime, Logout Time: $logoutTime")
    }
}


fun main() {
    val connection = connectToData()
    val getTime = Time()
    getTime.updateTime()
    println(getTime.getdate())
    insert(connection, getTime.getdate().toString(), getTime.getHour(),getTime.getMinute(),(getTime.getHour()+8),(getTime.getMinute()+10))
    print("User Name: ");val userId: String? = readLine()!!;
    print("User Password: ");val userPass: String? = readLine()!!;
    insertUser(connection, userId.toString(), userPass.toString())
}
