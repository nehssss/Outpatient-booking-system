package entity;

import javafx.scene.chart.XYChart;

import java.sql.*;

import static javafx.scene.chart.XYChart.*;

public class DepartmentMan {
    private  static Department department=new Department();

    //mysql驱动包名
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    //数据库连接地址
    private static final String URL = "jdbc:mysql://localhost:3306/demo"+"?serverTimezone=GMT%2B8";
    //用户名
    private static final String USER_NAME = "root";
    //密码
    private static final String PASSWORD = "7415963";

    private  String docb; //医生数据表名

    private String doctn; //医生名字

    private int docnum=0; //医生人数
    //单例模式
    private static DepartmentMan deman=null;
    private DepartmentMan(){}
    public static DepartmentMan getInstance(){
        if(deman == null)
            deman = new DepartmentMan();
        return deman;
    }
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     *得到医生信息
     * @param name
     */
    public void getMessage(String name){
            department.setName(name);
            Connection connection = null;
            try {
                //加载mysql的驱动类
                Class.forName(DRIVER_NAME);
                //获取数据库连接
                connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                //mysql查询语句
                String sql = "SELECT * FROM department";
                PreparedStatement prst = connection.prepareStatement(sql);
                //结果集
                ResultSet rs = prst.executeQuery();
                //获取医生所在的数据表
                while (rs.next()) {
                    if(rs.getString("name").equals(department.getName()))
                    {docb=rs.getString("doct");break;}
                }
                rs.close();
                prst.close();
                sql = "SELECT * FROM "+docb;
                 prst = connection.prepareStatement(sql);
                //结果集
                 rs = prst.executeQuery();
                 //获取医生数据
                 while(rs.next()){
                    Specialist doct=new Specialist();
                    doct.setName(rs.getString("name"));
                    doct.setPosition(rs.getString("position"));
                    doct.setSpecialty(rs.getString("specialty"));
                    doct.setRegistrationFee(rs.getInt("fee"));
                    doct.setWorktime(rs.getString("worktime"));
                    doct.setPatientnum(rs.getInt("patientnum"));
                    doct.setPatdb(rs.getString("patients"));
                    department.setDoctors(doct);
                     int before=department.getTotalfee();
                    int fee=doct.getRegistrationFee()*doct.getPatientnum()+before;
                    department.setTotalfee(fee);
                     docnum++;
                 }
                rs.close();
                prst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

    public void clear(){
        Department de=new Department();
     department=de;
    }

    /**
     * 得到病人
     * @param doct
     */
    public void getPatients(Specialist doct) {
        Connection connection = null;
        try {
            //加载mysql的驱动类
            Class.forName(DRIVER_NAME);
            //获取数据库连接
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            //mysql查询语句
            String sql = "SELECT * FROM " + doct.getPatdb();
            PreparedStatement prst = connection.prepareStatement(sql);
            //结果集
            ResultSet rs = prst.executeQuery();
            //获取病人数据
            while (rs.next()) {
                Patient pat=new Patient();
                pat.setID(rs.getString("id"));
                pat.setName(rs.getString("name"));
                pat.setPfee(rs.getInt("fee"));
                pat.setPtime(rs.getString("time"));
                doct.setPatients(pat);
            }
            rs.close();
            prst.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除病人
     * @param doc
     * @param pat
     */
    public void delPatient(Specialist doc,Patient pat){
        Connection connection = null;
        try {
            //加载mysql的驱动类
            Class.forName(DRIVER_NAME);
            //获取数据库连接
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            //mysql查询语句
            String sql = "delete from "+doc.getPatdb()+" where id = '"+pat.getID()+"'";
            PreparedStatement prst = connection.prepareStatement(sql);
            prst.executeUpdate();
            int patnum=doc.getPatientnum();
            doc.setPatientnum(--patnum);
            //删除操作
            sql="update "+docb+ " set patientnum = "+doc.getPatientnum()+" where name = '"+doc.getName()+"'";
            prst = connection.prepareStatement(sql);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 添加医生
     * @param d
     */
    public void setDoct(Specialist d){
        Connection connection = null;
        try {
            //加载mysql的驱动类
            Class.forName(DRIVER_NAME);
            //获取数据库连接
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            //mysql查询语句
            String sql = "insert into "+docb+"(name,position,specialty,fee,worktime,patientnum,patients) value(?,?,?,?,?,?,?)";
            //将医生信息加入数据表
            PreparedStatement prst = connection.prepareStatement(sql);
            prst.setString(1,d.getName());
            prst.setString(2,d.getPosition());
            prst.setString(3,d.getSpecialty());
            prst.setInt(4,d.getRegistrationFee());
            prst.setString(5,d.getWorktime());
            prst.setInt(6,0);
            String patdb="patient"+docb.substring(docb.length()-1)+Integer.toString((docnum+1));
            prst.setString(7,patdb);
            prst.executeUpdate();
            //创建对应的病人数据表
            sql="create table "+patdb+"(\n" +
                    "     id varchar(20),\n" +
                    "     name varchar(10),\n" +
                    "     fee int,\n" +
                    "     time varchar(10));";
            prst = connection.prepareStatement(sql);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除病人
     * @param d
     */
    public void delDoct(Specialist d){
        Connection connection = null;
        try {
            //加载mysql的驱动类
            Class.forName(DRIVER_NAME);
            //获取数据库连接
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            //mysql查询语句
            String sql = "delete from "+docb+" where name = '"+d.getName()+"'";
            PreparedStatement prst = connection.prepareStatement(sql);
            prst.executeUpdate();
            sql="drop table "+d.getPatdb();
            prst = connection.prepareStatement(sql);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获得每个部门的总收费值
     * @param series
     */
    public void totalFee(Series series){
        Connection connection = null;
        try {
            //加载mysql的驱动类
            Class.forName(DRIVER_NAME);
            //获取数据库连接
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            //mysql查询语句
            String sql = "select *from department";
            PreparedStatement prst = connection.prepareStatement(sql);
            //结果集
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                getMessage(rs.getString("name"));
                series.getData().add(new Data(department.getName(), department.getTotalfee()));
                department.setTotalfee(0);
            }
            rs.close();
            prst.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getDoctn() {
        return doctn;
    }

    public void setDoctn(String doctn) {
        this.doctn = doctn;
    }

    public  String getDocb() {
        return docb;
    }

    public  void setDocb(String docb) {
        this.docb = docb;
    }

    public int getDocnum() {
        return docnum;
    }

    public void setDocnum(int docnum) {
        this.docnum = docnum;
    }
}
