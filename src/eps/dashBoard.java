
package eps;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;



import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;



public class dashBoard extends javax.swing.JFrame  {

   
    public dashBoard() {
        initComponents();
        connection();
        mother_pane.setSelectedIndex(4);
        studentRegAuto();
        
        readAccTable();
        readStudentTable();
        readFeeTable();
        displayResult();
        
        edit1.setEnabled(false);
        remove1.setEnabled(false);
        
        edit_st.setEnabled(false);
        remove_st.setEnabled(false);
         
        reportPrint.setEnabled(false);
          
        paidBTN.setEnabled(false);
           
        
        
        
        
        
    }
    
    
 Connection conn;
 PreparedStatement pst,ps,ps1;
 ResultSet rs,rss,rs1;
 
 
   String idd;
    String newid;
    String usname;
    String ustype;
     public dashBoard(String userid, String uname,String utype) {
        initComponents();
        studentRegAuto();
        this.usname = uname;
        //dsname.setText(usname);
        
        this.ustype = utype;
        //dstype.setText(ustype);
        
        this.newid = userid;
        idd = newid;
   
         switch (ustype) {
            case "Admin":
                 mother_pane.setSelectedIndex(4);
                registerStudent.setEnabled(false);
                insertResults.setEnabled(false);
                accountsCorner.setEnabled(false);
                home.setEnabled(true);
                
                break;
                
            case "Secretary":
                 mother_pane.setSelectedIndex(4);
                insertResults.setEnabled(false);
                accountsCorner.setEnabled(false);
                addAccount.setEnabled(false);
                home.setEnabled(true);
                
                break;
                
                 case "Accounts":
                mother_pane.setSelectedIndex(4);
                insertResults.setEnabled(false);
                registerStudent.setEnabled(false);
                addAccount.setEnabled(false);   
                home.setEnabled(true);
                break;
            default:
                mother_pane.setSelectedIndex(4);
                registerStudent.setEnabled(false);
                accountsCorner.setEnabled(false);
                addAccount.setEnabled(false);
                home.setEnabled(true);
                break;
        }
        
    }
 
 
     public void connection()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/ed_sch", "root","");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
      
  
    
     public void setColor(JPanel pane1){
         pane1.setBackground(new Color(85,65,118));
     }
     
     public void resetColor(JPanel pane1){
         pane1.setBackground(new Color(54,33,89));
     }
     
     public void hover(JPanel pane1){
         pane1.setBackground(new Color(65,45,98));
     }
      public void hoveroff(JPanel pane1){
         pane1.setBackground(new Color(54,33,89));
     }
     
    private void readAccTable(){
        connection();
         try {
            pst = conn.prepareStatement("Select * from acctbl");
            rs = pst.executeQuery();
            ResultSetMetaData Rsm = rs.getMetaData();
            
            int c;
            c = Rsm.getColumnCount();
            
            DefaultTableModel df = (DefaultTableModel)accounttbl.getModel();
            df.setRowCount(0);
            
            while(rs.next()){
                
                Vector v2 = new Vector();
                
                for(int i = 1; i <= c; i++)
                {
                v2.add(rs.getString("accid"));
                v2.add(rs.getString("uname"));
                v2.add(rs.getString("utype"));
              //  v2.add(rs.getString("password"));
                v2.add(rs.getString("class"));
                v2.add(rs.getString("contact"));
                 
                
                }
                
                df.addRow(v2);
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void readStudentTable(){
        connection();
         try {
            pst = conn.prepareStatement("Select * from studentreg");
            rs = pst.executeQuery();
            ResultSetMetaData Rsm = rs.getMetaData();
            
            int c;
            c = Rsm.getColumnCount();
            
            DefaultTableModel df = (DefaultTableModel)stuentTable.getModel();
            df.setRowCount(0);
            
            while(rs.next()){
                
                Vector v3 = new Vector();
                
                for(int i = 1; i <= c; i++)
                {
                v3.add(rs.getString("stid"));
                v3.add(rs.getString("stdname"));
                v3.add(rs.getString("stdClass"));
                v3.add(rs.getString("pareName"));
                v3.add(rs.getString("pareCont"));
                 
                
                }
                
                df.addRow(v3);
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }              
 
    double ctuibal,newTui, finaTui = 0.0;
    private void readFeeTable(){
        
         connection();
         try {
            pst = conn.prepareStatement("Select * from feetbl");
            rs = pst.executeQuery();
            ResultSetMetaData Rsm = rs.getMetaData();
            
            int c;
            c = Rsm.getColumnCount();
            
            DefaultTableModel df = (DefaultTableModel)feetable.getModel();
            df.setRowCount(0);
            
            while(rs.next()){
                
               Vector v4 = new Vector();
                
                for(int i = 1; i <= c; i++)
                {
                v4.add(rs.getString("mid"));
                v4.add(rs.getString("mname"));
                v4.add(rs.getString("mclass"));
                v4.add(rs.getString("bal"));
                v4.add(rs.getString("lastpay"));
                
                
                }
                
                df.addRow(v4);
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        home5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        home6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        mainpane = new javax.swing.JPanel();
        navigationPane = new javax.swing.JPanel();
        home = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        addAccount = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        registerStudent = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        insertResults = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        accountsCorner = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        logout = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        mother_pane = new javax.swing.JTabbedPane();
        account = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel20 = new javax.swing.JLabel();
        aname = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        acont = new javax.swing.JTextField();
        utype = new javax.swing.JComboBox<>();
        apass = new javax.swing.JPasswordField();
        classcomb = new javax.swing.JComboBox<>();
        create1 = new javax.swing.JButton();
        edit1 = new javax.swing.JButton();
        remove1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        accounttbl = new javax.swing.JTable();
        jRadioButton1 = new javax.swing.JRadioButton();
        acc_id = new javax.swing.JTextField();
        secretary = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        stAID = new javax.swing.JLabel();
        parentN = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        classSt = new javax.swing.JComboBox<>();
        add_st = new javax.swing.JButton();
        edit_st = new javax.swing.JButton();
        remove_st = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        stuentTable = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        stName = new javax.swing.JTextField();
        perCont = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        cteacher = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        resuID = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        eng = new javax.swing.JTextField();
        sc = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        mtc = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        sst = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        submit = new javax.swing.JButton();
        resuName = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        reportPrint = new javax.swing.JButton();
        reeclas = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        reportPrint1 = new javax.swing.JButton();
        bursa = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        feetable = new javax.swing.JTable();
        jLabel42 = new javax.swing.JLabel();
        stnameF = new javax.swing.JTextField();
        stClF = new javax.swing.JComboBox<>();
        jLabel43 = new javax.swing.JLabel();
        searchBTN = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        ntuition = new javax.swing.JTextField();
        paidBTN = new javax.swing.JButton();
        homep = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();

        home5.setBackground(new java.awt.Color(85, 65, 118));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/student.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 204, 204));
        jLabel12.setText("Add Student");

        home6.setBackground(new java.awt.Color(85, 65, 118));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/student.png"))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 204, 204));
        jLabel14.setText("Add Student");

        javax.swing.GroupLayout home6Layout = new javax.swing.GroupLayout(home6);
        home6.setLayout(home6Layout);
        home6Layout.setHorizontalGroup(
            home6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(home6Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );
        home6Layout.setVerticalGroup(
            home6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(home6Layout.createSequentialGroup()
                .addGroup(home6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout home5Layout = new javax.swing.GroupLayout(home5);
        home5.setLayout(home5Layout);
        home5Layout.setHorizontalGroup(
            home5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(home5Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
            .addGroup(home5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(home5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(home6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        home5Layout.setVerticalGroup(
            home5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(home5Layout.createSequentialGroup()
                .addGroup(home5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(home5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(home5Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(home6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        mainpane.setBackground(new java.awt.Color(255, 255, 255));
        mainpane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        navigationPane.setBackground(new java.awt.Color(54, 33, 89));
        navigationPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                navigationPaneMouseMoved(evt);
            }
        });

        home.setBackground(new java.awt.Color(85, 65, 118));
        home.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                homeMouseMoved(evt);
            }
        });
        home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/home (1).png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("Home");

        javax.swing.GroupLayout homeLayout = new javax.swing.GroupLayout(home);
        home.setLayout(homeLayout);
        homeLayout.setHorizontalGroup(
            homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        homeLayout.setVerticalGroup(
            homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeLayout.createSequentialGroup()
                .addGroup(homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        addAccount.setBackground(new java.awt.Color(54, 33, 89));
        addAccount.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                addAccountMouseMoved(evt);
            }
        });
        addAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addAccountMouseClicked(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-friend.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Add Account");

        javax.swing.GroupLayout addAccountLayout = new javax.swing.GroupLayout(addAccount);
        addAccount.setLayout(addAccountLayout);
        addAccountLayout.setHorizontalGroup(
            addAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addAccountLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        addAccountLayout.setVerticalGroup(
            addAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addAccountLayout.createSequentialGroup()
                .addGroup(addAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        registerStudent.setBackground(new java.awt.Color(54, 33, 89));
        registerStudent.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                registerStudentMouseMoved(evt);
            }
        });
        registerStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerStudentMouseClicked(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/student.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Register Student");

        javax.swing.GroupLayout registerStudentLayout = new javax.swing.GroupLayout(registerStudent);
        registerStudent.setLayout(registerStudentLayout);
        registerStudentLayout.setHorizontalGroup(
            registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerStudentLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        registerStudentLayout.setVerticalGroup(
            registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
        );

        insertResults.setBackground(new java.awt.Color(54, 33, 89));
        insertResults.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                insertResultsMouseMoved(evt);
            }
        });
        insertResults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                insertResultsMouseClicked(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/checklist.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("Student Results");

        javax.swing.GroupLayout insertResultsLayout = new javax.swing.GroupLayout(insertResults);
        insertResults.setLayout(insertResultsLayout);
        insertResultsLayout.setHorizontalGroup(
            insertResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(insertResultsLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        insertResultsLayout.setVerticalGroup(
            insertResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(insertResultsLayout.createSequentialGroup()
                .addGroup(insertResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        accountsCorner.setBackground(new java.awt.Color(54, 33, 89));
        accountsCorner.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                accountsCornerMouseMoved(evt);
            }
        });
        accountsCorner.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accountsCornerMouseClicked(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/income.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 204, 204));
        jLabel10.setText("Accounts");

        javax.swing.GroupLayout accountsCornerLayout = new javax.swing.GroupLayout(accountsCorner);
        accountsCorner.setLayout(accountsCornerLayout);
        accountsCornerLayout.setHorizontalGroup(
            accountsCornerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accountsCornerLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        accountsCornerLayout.setVerticalGroup(
            accountsCornerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
        );

        logout.setBackground(new java.awt.Color(54, 33, 89));
        logout.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        logout.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                logoutMouseMoved(evt);
            }
        });
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMouseClicked(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/log-out.png"))); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(204, 204, 204));
        jLabel16.setText("Log out");

        javax.swing.GroupLayout logoutLayout = new javax.swing.GroupLayout(logout);
        logout.setLayout(logoutLayout);
        logoutLayout.setHorizontalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoutLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        logoutLayout.setVerticalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutLayout.createSequentialGroup()
                .addGroup(logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel26.setFont(new java.awt.Font("Elephant", 1, 55)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("EDger");

        jSeparator3.setMinimumSize(new java.awt.Dimension(50, 20));

        javax.swing.GroupLayout navigationPaneLayout = new javax.swing.GroupLayout(navigationPane);
        navigationPane.setLayout(navigationPaneLayout);
        navigationPaneLayout.setHorizontalGroup(
            navigationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navigationPaneLayout.createSequentialGroup()
                .addGroup(navigationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(navigationPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(navigationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(logout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(navigationPaneLayout.createSequentialGroup()
                                .addGroup(navigationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(accountsCorner, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(insertResults, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(registerStudent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(addAccount, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(home, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 2, Short.MAX_VALUE))))
                    .addGroup(navigationPaneLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(navigationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        navigationPaneLayout.setVerticalGroup(
            navigationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navigationPaneLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registerStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(insertResults, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(accountsCorner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        mainpane.add(navigationPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 630));

        account.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(0, 0, 204));
        jPanel4.setPreferredSize(new java.awt.Dimension(866, 84));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Add Account");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(343, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel20.setBackground(new java.awt.Color(54, 33, 89));
        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(54, 33, 89));
        jLabel20.setText("UserName");

        aname.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        aname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        jLabel22.setBackground(new java.awt.Color(153, 153, 153));
        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(54, 33, 89));
        jLabel22.setText("UserType");

        jLabel23.setBackground(new java.awt.Color(54, 33, 89));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(54, 33, 89));
        jLabel23.setText("Password");

        jLabel24.setBackground(new java.awt.Color(153, 153, 153));
        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(54, 33, 89));
        jLabel24.setText("Class");

        jLabel25.setBackground(new java.awt.Color(153, 153, 153));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(54, 33, 89));
        jLabel25.setText("Contact");

        acont.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        acont.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        utype.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        utype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Secretary", "Accounts", "Class Teacher" }));

        apass.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        apass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        classcomb.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        classcomb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "P1", "P2", "P3", "P4", "P5", "P6", "P7" }));

        create1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        create1.setForeground(new java.awt.Color(0, 0, 204));
        create1.setText("CREATE");
        create1.setBorder(null);
        create1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                create1ActionPerformed(evt);
            }
        });

        edit1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        edit1.setForeground(new java.awt.Color(0, 0, 204));
        edit1.setText("EDIT");
        edit1.setBorder(null);
        edit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit1ActionPerformed(evt);
            }
        });

        remove1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        remove1.setForeground(new java.awt.Color(0, 0, 204));
        remove1.setText("REMOVE");
        remove1.setBorder(null);
        remove1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remove1ActionPerformed(evt);
            }
        });

        accounttbl.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        accounttbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Account ID", "UserName", "User Type", "Class", "Contact"
            }
        ));
        accounttbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accounttblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(accounttbl);
        if (accounttbl.getColumnModel().getColumnCount() > 0) {
            accounttbl.getColumnModel().getColumn(0).setPreferredWidth(50);
            accounttbl.getColumnModel().getColumn(1).setResizable(false);
            accounttbl.getColumnModel().getColumn(1).setPreferredWidth(100);
            accounttbl.getColumnModel().getColumn(2).setResizable(false);
            accounttbl.getColumnModel().getColumn(2).setPreferredWidth(60);
            accounttbl.getColumnModel().getColumn(3).setResizable(false);
            accounttbl.getColumnModel().getColumn(3).setPreferredWidth(60);
            accounttbl.getColumnModel().getColumn(4).setResizable(false);
            accounttbl.getColumnModel().getColumn(4).setPreferredWidth(60);
        }

        jRadioButton1.setText("Not A teacher");

        javax.swing.GroupLayout accountLayout = new javax.swing.GroupLayout(account);
        account.setLayout(accountLayout);
        accountLayout.setHorizontalGroup(
            accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accountLayout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(create1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(edit1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(remove1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126)
                .addComponent(jRadioButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, accountLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 763, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
            .addGroup(accountLayout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(accountLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addGroup(accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(aname, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(acc_id, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(accountLayout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(utype, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(accountLayout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(apass, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addGroup(accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addGroup(accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(acont, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(classcomb, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(59, 59, 59))
                    .addGroup(accountLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(41, Short.MAX_VALUE))))
        );
        accountLayout.setVerticalGroup(
            accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accountLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(accountLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(classcomb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)))
                    .addGroup(accountLayout.createSequentialGroup()
                        .addGroup(accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(accountLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(utype, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22))
                                .addGap(24, 24, 24))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, accountLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(acc_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(apass, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(acont, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)
                            .addComponent(aname, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))))
                .addGroup(accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(accountLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(create1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(edit1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(remove1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, accountLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton1)
                        .addGap(66, 66, 66)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(492, Short.MAX_VALUE))
        );

        mother_pane.addTab("tab1", account);

        secretary.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(0, 0, 204));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Register Student");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(326, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel29.setBackground(new java.awt.Color(153, 153, 153));
        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(54, 33, 89));
        jLabel29.setText("ID");

        jLabel30.setBackground(new java.awt.Color(54, 33, 89));
        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(54, 33, 89));
        jLabel30.setText("Parent's Name");

        stAID.setBackground(new java.awt.Color(153, 153, 153));
        stAID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        stAID.setForeground(new java.awt.Color(102, 102, 102));
        stAID.setText("jLabel21");

        parentN.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        parentN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        jLabel32.setBackground(new java.awt.Color(54, 33, 89));
        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(54, 33, 89));
        jLabel32.setText("Parent's Contact");

        classSt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        classSt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "P1", "P2", "P3", "P4", "P5", "P6", "P7" }));

        add_st.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        add_st.setForeground(new java.awt.Color(0, 0, 204));
        add_st.setText("Register");
        add_st.setBorder(null);
        add_st.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_stActionPerformed(evt);
            }
        });

        edit_st.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        edit_st.setForeground(new java.awt.Color(0, 0, 204));
        edit_st.setText("EDIT");
        edit_st.setBorder(null);
        edit_st.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_stActionPerformed(evt);
            }
        });

        remove_st.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        remove_st.setForeground(new java.awt.Color(0, 0, 204));
        remove_st.setText("REMOVE");
        remove_st.setBorder(null);
        remove_st.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remove_stActionPerformed(evt);
            }
        });

        stuentTable.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        stuentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Student Name", "Class", "Parent's Name", "Parent's Contact"
            }
        ));
        stuentTable.setSelectionBackground(new java.awt.Color(0, 0, 204));
        stuentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stuentTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(stuentTable);
        if (stuentTable.getColumnModel().getColumnCount() > 0) {
            stuentTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            stuentTable.getColumnModel().getColumn(1).setResizable(false);
            stuentTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            stuentTable.getColumnModel().getColumn(2).setResizable(false);
            stuentTable.getColumnModel().getColumn(2).setPreferredWidth(60);
            stuentTable.getColumnModel().getColumn(3).setResizable(false);
            stuentTable.getColumnModel().getColumn(3).setPreferredWidth(60);
            stuentTable.getColumnModel().getColumn(4).setResizable(false);
            stuentTable.getColumnModel().getColumn(4).setPreferredWidth(60);
        }

        jLabel34.setBackground(new java.awt.Color(54, 33, 89));
        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(54, 33, 89));
        jLabel34.setText("Student Name");

        stName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        stName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        perCont.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        perCont.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        jLabel35.setBackground(new java.awt.Color(54, 33, 89));
        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(54, 33, 89));
        jLabel35.setText("Class");

        javax.swing.GroupLayout secretaryLayout = new javax.swing.GroupLayout(secretary);
        secretary.setLayout(secretaryLayout);
        secretaryLayout.setHorizontalGroup(
            secretaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, secretaryLayout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addGroup(secretaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(secretaryLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, secretaryLayout.createSequentialGroup()
                        .addGroup(secretaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(secretaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 763, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(secretaryLayout.createSequentialGroup()
                                    .addComponent(add_st, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(edit_st, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(remove_st, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(secretaryLayout.createSequentialGroup()
                                .addGroup(secretaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, secretaryLayout.createSequentialGroup()
                                        .addComponent(jLabel30)
                                        .addGap(18, 18, 18))
                                    .addGroup(secretaryLayout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addGap(102, 102, 102)))
                                .addGroup(secretaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, secretaryLayout.createSequentialGroup()
                                        .addComponent(stAID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(28, 28, 28)
                                        .addComponent(jLabel34)
                                        .addGap(34, 34, 34)
                                        .addComponent(stName, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, secretaryLayout.createSequentialGroup()
                                        .addComponent(parentN, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel32)
                                        .addGap(18, 18, 18)
                                        .addComponent(perCont, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(secretaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35)
                                    .addComponent(classSt, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(80, 80, 80))))
        );
        secretaryLayout.setVerticalGroup(
            secretaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(secretaryLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(secretaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(secretaryLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(secretaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(stAID)
                            .addComponent(jLabel29)
                            .addComponent(jLabel34)
                            .addComponent(stName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addGroup(secretaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(parentN, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32)
                            .addComponent(perCont, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46))
                    .addGroup(secretaryLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(classSt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(secretaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add_st, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edit_st, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(remove_st, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(441, Short.MAX_VALUE))
        );

        mother_pane.addTab("tab2", secretary);

        jPanel6.setBackground(new java.awt.Color(0, 0, 204));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Student Results");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(343, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel36.setBackground(new java.awt.Color(153, 153, 153));
        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(54, 33, 89));
        jLabel36.setText("ID");

        jLabel38.setBackground(new java.awt.Color(54, 33, 89));
        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(54, 33, 89));
        jLabel38.setText("Student Name");

        resuID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        resuID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(54, 33, 89)), "Student Marks", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(54, 33, 89))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(54, 33, 89));
        jLabel17.setText("Math");

        eng.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        eng.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        sc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(54, 33, 89));
        jLabel39.setText("English");

        mtc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        mtc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(54, 33, 89));
        jLabel40.setText("Science");

        sst.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sst.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(54, 33, 89));
        jLabel41.setText("SST");

        submit.setBackground(new java.awt.Color(85, 65, 118));
        submit.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        submit.setForeground(new java.awt.Color(255, 255, 255));
        submit.setText("Submit");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sst, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(eng, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(86, 86, 86)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mtc, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sc, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(57, 57, 57)
                    .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(101, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(mtc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(sc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eng, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(sst, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(76, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(234, 234, 234)
                    .addComponent(submit, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addGap(12, 12, 12)))
        );

        resuName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        resuName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        resultTable.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        resultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Student Name", "Class", "Math", "Science", "English", "SST", "Total Marks", "Average Marks", "Aggregates"
            }
        ));
        resultTable.setSelectionBackground(new java.awt.Color(0, 0, 204));
        jScrollPane3.setViewportView(resultTable);
        if (resultTable.getColumnModel().getColumnCount() > 0) {
            resultTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            resultTable.getColumnModel().getColumn(1).setResizable(false);
            resultTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            resultTable.getColumnModel().getColumn(2).setResizable(false);
            resultTable.getColumnModel().getColumn(2).setPreferredWidth(60);
            resultTable.getColumnModel().getColumn(3).setResizable(false);
            resultTable.getColumnModel().getColumn(3).setPreferredWidth(60);
            resultTable.getColumnModel().getColumn(4).setResizable(false);
            resultTable.getColumnModel().getColumn(4).setPreferredWidth(60);
            resultTable.getColumnModel().getColumn(5).setResizable(false);
            resultTable.getColumnModel().getColumn(5).setPreferredWidth(60);
            resultTable.getColumnModel().getColumn(8).setResizable(false);
            resultTable.getColumnModel().getColumn(8).setPreferredWidth(60);
        }

        reportPrint.setBackground(new java.awt.Color(85, 65, 118));
        reportPrint.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        reportPrint.setForeground(new java.awt.Color(255, 255, 255));
        reportPrint.setText("Print Report");
        reportPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportPrintActionPerformed(evt);
            }
        });

        reeclas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        reeclas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "P1", "P2", "P3", "P4", "P5", "P6", "P7" }));

        jLabel31.setBackground(new java.awt.Color(153, 153, 153));
        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(54, 33, 89));
        jLabel31.setText("Class");

        reportPrint1.setBackground(new java.awt.Color(85, 65, 118));
        reportPrint1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        reportPrint1.setForeground(new java.awt.Color(255, 255, 255));
        reportPrint1.setText("SEARCH");
        reportPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportPrint1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cteacherLayout = new javax.swing.GroupLayout(cteacher);
        cteacher.setLayout(cteacherLayout);
        cteacherLayout.setHorizontalGroup(
            cteacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cteacherLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(cteacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cteacherLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cteacherLayout.createSequentialGroup()
                        .addGroup(cteacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(cteacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cteacherLayout.createSequentialGroup()
                                .addComponent(resuName, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 542, Short.MAX_VALUE))
                            .addGroup(cteacherLayout.createSequentialGroup()
                                .addComponent(resuID, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(jLabel31)
                                .addGap(18, 18, 18)
                                .addComponent(reeclas, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(reportPrint1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(reportPrint)
                                .addGap(12, 12, 12))))
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );
        cteacherLayout.setVerticalGroup(
            cteacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cteacherLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addGroup(cteacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cteacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel36)
                        .addComponent(resuID, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cteacherLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(cteacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(reeclas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31)
                            .addComponent(reportPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(reportPrint1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cteacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(resuName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(cteacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(406, Short.MAX_VALUE))
        );

        mother_pane.addTab("tab3", cteacher);

        jPanel7.setBackground(new java.awt.Color(0, 0, 204));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Tuition Payment");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(316, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        feetable.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        feetable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Student Name", "Student Class", "Balance", "Last Payment"
            }
        ));
        jScrollPane4.setViewportView(feetable);
        if (feetable.getColumnModel().getColumnCount() > 0) {
            feetable.getColumnModel().getColumn(0).setPreferredWidth(50);
            feetable.getColumnModel().getColumn(1).setResizable(false);
            feetable.getColumnModel().getColumn(1).setPreferredWidth(100);
            feetable.getColumnModel().getColumn(2).setResizable(false);
            feetable.getColumnModel().getColumn(2).setPreferredWidth(60);
            feetable.getColumnModel().getColumn(3).setResizable(false);
            feetable.getColumnModel().getColumn(3).setPreferredWidth(60);
            feetable.getColumnModel().getColumn(4).setResizable(false);
            feetable.getColumnModel().getColumn(4).setPreferredWidth(60);
        }

        jLabel42.setBackground(new java.awt.Color(54, 33, 89));
        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(54, 33, 89));
        jLabel42.setText("Student Name");

        stnameF.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        stnameF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        stClF.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        stClF.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "P1", "P2", "P3", "P4", "P5", "P6", "P7" }));

        jLabel43.setBackground(new java.awt.Color(54, 33, 89));
        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(54, 33, 89));
        jLabel43.setText("Class");

        searchBTN.setBackground(new java.awt.Color(0, 255, 255));
        searchBTN.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        searchBTN.setForeground(new java.awt.Color(255, 255, 255));
        searchBTN.setText("Search");
        searchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBTNActionPerformed(evt);
            }
        });

        jLabel44.setBackground(new java.awt.Color(54, 33, 89));
        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(54, 33, 89));
        jLabel44.setText("Amout");

        ntuition.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ntuition.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        paidBTN.setBackground(new java.awt.Color(85, 65, 118));
        paidBTN.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        paidBTN.setForeground(new java.awt.Color(255, 255, 255));
        paidBTN.setText("PAID");
        paidBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paidBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bursaLayout = new javax.swing.GroupLayout(bursa);
        bursa.setLayout(bursaLayout);
        bursaLayout.setHorizontalGroup(
            bursaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bursaLayout.createSequentialGroup()
                .addGroup(bursaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bursaLayout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addGroup(bursaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 763, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(bursaLayout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addGap(18, 18, 18)
                                .addGroup(bursaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(bursaLayout.createSequentialGroup()
                                        .addComponent(jLabel44)
                                        .addGap(18, 18, 18)
                                        .addComponent(ntuition, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(55, 55, 55)
                                        .addComponent(paidBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(bursaLayout.createSequentialGroup()
                                        .addComponent(stnameF, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel43)
                                        .addGap(34, 34, 34)
                                        .addComponent(stClF, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(38, 38, 38)
                                        .addComponent(searchBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(bursaLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        bursaLayout.setVerticalGroup(
            bursaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bursaLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addGroup(bursaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(stnameF, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43)
                    .addComponent(stClF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(bursaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(ntuition, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paidBTN))
                .addGap(54, 54, 54)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(444, Short.MAX_VALUE))
        );

        mother_pane.addTab("tab4", bursa);

        homep.setLayout(null);

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pp2.jpg"))); // NOI18N
        jLabel27.setText("jLabel27");
        homep.add(jLabel27);
        jLabel27.setBounds(0, 0, 970, 640);

        mother_pane.addTab("tab5", homep);

        mainpane.add(mother_pane, new org.netbeans.lib.awtextra.AbsoluteConstraints(322, -29, 970, 1020));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainpane, javax.swing.GroupLayout.PREFERRED_SIZE, 1281, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainpane, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
   

/////////  ////////    First Panel    ///////  ///////   ///////      //////
    private void create1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create1ActionPerformed
      
        String acc_uname = aname.getText();
        String acc_utype = utype.getSelectedItem().toString();
        String acc_pass = apass.getText();
        String acc_class = classcomb.getSelectedItem().toString();
        String acc_cont = acont.getText();
        String ena = "NOT A TEACHER";
        connection();
        
        if(jRadioButton1.isEnabled()){
            try {
            pst = conn.prepareStatement("insert into acctbl(uname,utype,password,class,contact) values(?,?,?,?,?)");
            pst.setString(1, acc_uname);
            pst.setString(2, acc_utype);
            pst.setString(3, acc_pass);
            pst.setString(4, ena);
            pst.setString(5, acc_cont);
            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "Account Created Successfully!!!");
            
            aname.setText("");
            aname.requestFocus();
            utype.setSelectedIndex(-1);
            apass.setText("");
            classcomb.setSelectedIndex(-1);
            acont.setText("");
            readAccTable();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
        else{
           
            try {
            pst = conn.prepareStatement("insert into acctbl(uname,utype,password,class,contact) values(?,?,?,?,?)");
            pst.setString(1, acc_uname);
            pst.setString(2, acc_utype);
            pst.setString(3, acc_pass);
            pst.setString(4, acc_class);
            pst.setString(5, acc_cont);
            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "Account Created Successfully!!!");
            
            aname.setText("");
            aname.requestFocus();
            utype.setSelectedIndex(-1);
            apass.setText("");
            classcomb.setSelectedIndex(-1);
            acont.setText("");
            readAccTable();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }
        
        
        
        
        
    }//GEN-LAST:event_create1ActionPerformed

    private void edit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit1ActionPerformed
       String acc_uname = aname.getText();
        String acc_utype = utype.getSelectedItem().toString();
        String acc_pass = apass.getText();
        String acc_class = classcomb.getSelectedItem().toString();
        String acc_cont = acont.getText();
        String ena = "NOT A TEACHER";
        String acid = acc_id.getText();
        
        try {
        if(jRadioButton1.isSelected()){
           
            
               pst = conn.prepareStatement("update acctbl set uname = ?, utype = ?, class = ?, contact = ? where accid = ? " );
               pst.setString(1, acc_uname);
               pst.setString(2, acc_utype);
               //pst.setString(3, acc_pass);
               pst.setString(3, ena);
               pst.setString(4, acc_cont);
               pst.setString(5, acid);
               pst.executeUpdate();
               
               JOptionPane.showMessageDialog(this, "Account Updated successfully!!!");
                aname.setText("");
                aname.requestFocus();
                utype.setSelectedIndex(-1);
                apass.setText("");
                classcomb.setSelectedIndex(0);
                acont.setText("");
                acc_id.setText("");
                jRadioButton1.setSelected(false);
                readAccTable();
                
               create1.setEnabled(true);
              edit1.setEnabled(false);
              remove1.setEnabled(false);
          
            
        }else if(!jRadioButton1.isSelected())
        
        {                          
               pst = conn.prepareStatement("update acctbl set uname = ?, utype = ?, class = ?, contact = ? where accid = ? " );
               pst.setString(1, acc_uname);
               pst.setString(2, acc_utype);
               //pst.setString(3, acc_pass);
               pst.setString(3, acc_class);
               pst.setString(4, acc_cont);
               pst.setString(5, acid);
               pst.executeUpdate();
               
               JOptionPane.showMessageDialog(this, "Account Updated successfully!!!");
                aname.setText("");
                aname.requestFocus();
                utype.setSelectedIndex(-1);
                apass.setText("");
                classcomb.setSelectedIndex(-1);
                acont.setText("");
                acc_id.setText("");
                 jRadioButton1.disable();
                  jRadioButton1.setSelected(false);
                readAccTable();
               
                
               create1.setEnabled(true);
              edit1.setEnabled(false);
              remove1.setEnabled(false);
          
           
        }
        
         } catch (SQLException ex) {
               Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
           }
        
        
    }//GEN-LAST:event_edit1ActionPerformed

    private void remove1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remove1ActionPerformed
      
        String acid = acc_id.getText();
        connection();
        
        if(jRadioButton1.isEnabled()){
            try {
            pst = conn.prepareStatement("delete from acctbl where accid = ?");
            pst.setString(1, acid);
            
            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "Account Removed Successfully!!!");
            
            aname.setText("");
            aname.requestFocus();
            utype.setSelectedIndex(-1);
            apass.setText("");
            classcomb.setSelectedIndex(-1);
            acont.setText("");
            readAccTable();
            
            create1.setEnabled(true);
            edit1.setEnabled(false);
            remove1.setEnabled(false);
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        
    }//GEN-LAST:event_remove1ActionPerformed

    

    /////////////////////////     Second Panel           ///////////////////////////////////
    
    
    
    public void studentRegAuto(){
        
        
        try {
            connection();
            Statement s = conn.createStatement();
            rs = s.executeQuery("select MAX(stid) from studentreg");
            rs.next();
            rs.getString("MAX(stid)");
            
            if(rs.getString("MAX(stid)") == null){
                stAID.setText("C2202001B001");
            }
            else{
                Long id = Long.parseLong(rs.getString("MAX(stid)").substring(9,rs.getString("MAX(stid)").length()));
                id++;
                
                stAID.setText("C2202001B" + String.format("%03d", id));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void insertTOAccouts(){
        
        // tuition
        double tuition;
        String selectedClass = classSt.getSelectedItem().toString();
        
        
        switch(selectedClass){
            
            case "P1":
                tuition = 550000;
                break;
            case "P2":
                tuition = 750000;
                break;
            case "P3":
                tuition = 950000;
                break;
            case "P4":
                tuition = 1150000;
                break;
            case "P5":
                tuition = 1350000;
                break;
            case "P6":
                tuition = 1550000;
                break;
            default:
                tuition = 2000000;
            
        }
                
    ////////////////////////////////////////////////////////////////////
        
        String stuid = stAID.getText();
        String stuname = stName.getText();
        String stuFee = Double.toString(tuition);
        String stuClass = classSt.getSelectedItem().toString();
        
        connection();
        
        try {
            pst = conn.prepareStatement("insert into feetbl(mid,mname,mclass,bal) values(?,?,?,?)");
            pst.setString(1, stuid);
            pst.setString(2, stuname);
            pst.setString(3, stuClass);
            pst.setString(4, stuFee);
            
            pst.executeUpdate();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
    private void updateToAccounts(){
        double tuition;
        String selectedClass = classSt.getSelectedItem().toString();
        
        
        switch(selectedClass){
            
            case "P1":
                tuition = 550000;
                break;
            case "P2":
                tuition = 750000;
                break;
            case "P3":
                tuition = 950000;
                break;
            case "P4":
                tuition = 1150000;
                break;
            case "P5":
                tuition = 1350000;
                break;
            case "P6":
                tuition = 1550000;
                break;
            default:
                tuition = 2000000;
            
        }
                
    ////////////////////////////////////////////////////////////////////
        
        String stuid = stAID.getText();
        String stuname = stName.getText();
        String stuFee = Double.toString(tuition);
        String stuClass = classSt.getSelectedItem().toString();
        
        
        connection();
        
        try {
            pst = conn.prepareStatement("update feetbl set mname = ?, mclass = ?,bal = ? where mid = ?");
            pst.setString(1, stuname);
            pst.setString(2, stuClass);
            pst.setString(3, stuFee);
            pst.setString(4, stuid);
            pst.executeUpdate();
          
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private void deleteFromAccounts(){
        
          String stuid = stAID.getText();
        
         connection();
        
        try {
            pst = conn.prepareStatement("delete from feetbl where mid = ?");
            pst.setString(1, stuid);
            pst.executeUpdate();
           
            
           
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
    private void add_stActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_stActionPerformed
      
        String stuid = stAID.getText();
        String stuname = stName.getText();
        String pareName = parentN.getText();
        String parentPhone = perCont.getText();
        String stuClass = classSt.getSelectedItem().toString();
        
        connection();
        
        try {
            pst = conn.prepareStatement("insert into studentreg(stid,stdname,stdClass,pareName,pareCont) values(?,?,?,?,?)");
            pst.setString(1, stuid);
            pst.setString(2, stuname);
            pst.setString(3, stuClass);
            pst.setString(4, pareName);
            pst.setString(5, parentPhone);
            pst.executeUpdate();
            insertTOAccouts();
            JOptionPane.showMessageDialog(this, "Student has been Registered Successfully!!");
            
            stName.setText("");
            stName.requestFocus();
            parentN.setText(""); 
            perCont.setText("");
            classSt.setSelectedIndex(0);
            studentRegAuto();
            readStudentTable();
            readFeeTable();
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_add_stActionPerformed

    private void edit_stActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_stActionPerformed
        
        String stuid = stAID.getText();
        String stuname = stName.getText();
        String pareName = parentN.getText();
        String parentPhone = perCont.getText();
        String stuClass = classSt.getSelectedItem().toString();
        
        connection();
        
        try {
            pst = conn.prepareStatement("update studentreg set stdname = ?, stdClass = ?,pareName = ?,pareCont = ? where stid = ?");
            pst.setString(1, stuname);
            pst.setString(2, stuClass);
            pst.setString(3, pareName);
            pst.setString(4, parentPhone);
            pst.setString(5, stuid);
            pst.executeUpdate();
            updateToAccounts();
            JOptionPane.showMessageDialog(this, "Student information has been Updated Successfully!!");
            
            stName.setText("");
            stName.requestFocus();
            parentN.setText(""); 
            perCont.setText("");
            classSt.setSelectedIndex(0);
            studentRegAuto();
            readStudentTable();
            readFeeTable();
            
            
            add_st.setEnabled(true);
            edit_st.setEnabled(false);
            remove_st.setEnabled(false);
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }//GEN-LAST:event_edit_stActionPerformed

    private void remove_stActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remove_stActionPerformed
      
        String stuid = stAID.getText();
        
        
        connection();
        
        try {
            pst = conn.prepareStatement("delete from studentreg where stid = ?");
            pst.setString(1, stuid);
            pst.executeUpdate();
            deleteFromAccounts();
            JOptionPane.showMessageDialog(this, "Student information has been Deleted Successfully!!");
            
            stName.setText("");
            stName.requestFocus();
            parentN.setText(""); 
            perCont.setText("");
            classSt.setSelectedIndex(0);
            studentRegAuto();
            readStudentTable();
            readFeeTable();
            
            add_st.setEnabled(true);
            edit_st.setEnabled(false);
            remove_st.setEnabled(false);
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
    }//GEN-LAST:event_remove_stActionPerformed

    private void accounttblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accounttblMouseClicked
        
        DefaultTableModel d = (DefaultTableModel)accounttbl.getModel();
        int selectedIndex = accounttbl.getSelectedRow();
        
        acc_id.setText(d.getValueAt(selectedIndex, 0).toString());
        aname.setText(d.getValueAt(selectedIndex, 1).toString());
        utype.setSelectedItem(d.getValueAt(selectedIndex, 2));
        classcomb.setSelectedItem(d.getValueAt(selectedIndex, 3).toString());
        acont.setText(d.getValueAt(selectedIndex, 4).toString());
        
        
        
             
        create1.setEnabled(false);
        edit1.setEnabled(true);
        remove1.setEnabled(true);
       
       
        
    }//GEN-LAST:event_accounttblMouseClicked

    private void stuentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stuentTableMouseClicked
       
        DefaultTableModel d = (DefaultTableModel)stuentTable.getModel();
        int selectedIndex = stuentTable.getSelectedRow();
        
        stAID.setText(d.getValueAt(selectedIndex, 0).toString());
        stName.setText(d.getValueAt(selectedIndex, 1).toString());
        classSt.setSelectedItem(d.getValueAt(selectedIndex, 2));
        parentN.setText(d.getValueAt(selectedIndex, 3).toString());
        perCont.setText(d.getValueAt(selectedIndex, 4).toString());
        
       
         add_st.setEnabled(false);
            edit_st.setEnabled(true);
            remove_st.setEnabled(true);
        
    }//GEN-LAST:event_stuentTableMouseClicked

    
    
    //////////////////////////////// Fourth Pane  /////////////////////////////////////
    private void printReceipt(){
        try {
            String fstname = stnameF.getText();
            
            String ssid = "" ;
            connection();
            
            
            ps1 = conn.prepareStatement("Select * from feetbl where mname=?");
            ps1.setString(1, fstname);
            rs1 = ps1.executeQuery();
            
            if(rs1.next()){
                ssid = rs1.getString(1);
            }
            
            
            
            
            HashMap ha = new HashMap();
            ha.put("invo", ssid);
            
            readFeeTable();
           
            JasperDesign jd;
            try {
                jd = JRXmlLoader.load("C:\\Users\\Dell\\Desktop\\java work\\Projects\\EdgerPrimarySchool\\src\\eps\\receipt.jrxml");
                JasperReport jsr = JasperCompileManager.compileReport(jd);
            JasperPrint jprt = JasperFillManager.fillReport(jsr, ha, conn);
            JasperViewer.viewReport(jprt);
            
            
            } catch (JRException ex) {
                Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
    
    private void paidBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paidBTNActionPerformed
      
        String fstname = stnameF.getText();
        String fstClass = stClF.getSelectedItem().toString();
        ////
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat datee = new SimpleDateFormat("MMMMM dd, yyyy");//for full name of month use MMMMM. for digit month use MM
        Date dat = cal.getTime();
        String storeDate = datee.format(dat);
        /////
        
        String dateprinted = storeDate;
        newTui = Double.parseDouble(ntuition.getText()) ;
        finaTui = ctuibal - newTui;
        String fina = Double.toString(finaTui);
        String lassPay = Double.toString(newTui);
        
        
        
        if(newTui > ctuibal){
            JOptionPane.showMessageDialog(this, "There is An ERROR in the Amount Enter. Please Insert Amount Again!!");
            /*
            String rAmount = JOptionPane.showInputDialog(this, "Enter Submited Amount");
            ntuition.setText(rAmount);
            */
            readFeeTable();
            
        }else{
            
            
            try {
                pst = conn.prepareStatement("update feetbl set bal = ?, lastpay = ?, date = ? where mname = ? and mclass = ?");
                pst.setString(1, fina);
                pst.setString(2, lassPay);
                pst.setString(3, dateprinted);
                pst.setString(4, fstname);
                pst.setString(5, fstClass);
                pst.executeUpdate();
                
                JOptionPane.showMessageDialog(this, "Tuition Data Updated Succefully!!!");
                 printReceipt();
                stnameF.setText("");
                ntuition.setText("");
                stnameF.requestFocus();
                stClF.setSelectedIndex(-1);
                
               
                
                
            } catch (SQLException ex) {
                Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        }
        
        
    }//GEN-LAST:event_paidBTNActionPerformed

    private void searchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBTNActionPerformed
        
        String fstname = stnameF.getText();
        String fstClass = stClF.getSelectedItem().toString();
        
        
        
        try {
            
            ps = conn.prepareStatement("Select * from feetbl where mname=? and mclass = ?");
            ps.setString(1, fstname);
            ps.setString(2, fstClass);
            rss = pst.executeQuery();
           
            if(rss.next()){
                
            pst = conn.prepareStatement("Select * from feetbl where mname=? and mclass = ?");
            pst.setString(1, fstname);
            pst.setString(2, fstClass);
            rs = pst.executeQuery();
            
         
                ResultSetMetaData Rsm = rs.getMetaData();
            int cc;
            cc = Rsm.getColumnCount();
            
            DefaultTableModel dff = (DefaultTableModel)feetable.getModel();
            dff.setRowCount(0);
            
            while(rs.next()){
                
                Vector v5 = new Vector();
                
                for(int i = 1; i <= cc; i++)
                {
                 v5.add(rs.getString("mid"));
                v5.add(rs.getString("mname"));
                v5.add(rs.getString("mclass"));
                v5.add(rs.getString("bal"));
                v5.add(rs.getString("lastpay"));
                
                }
                ctuibal =  Double.valueOf(rs.getString("bal"));
                dff.addRow(v5);
                
            }
            String nnto = JOptionPane.showInputDialog(this, "Enter Amount Submited...");
            ntuition.setText(nnto);
           readFeeTable();
                
                paidBTN.setEnabled(true);
            }
            else if(!rss.next()){
                
                 JOptionPane.showMessageDialog(this, "Incorrect student details. Please enter Again!!");
               stnameF.setText("");
               stnameF.requestFocus();
               stClF.setSelectedIndex(-1);
                
            }
            
       
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        
        
        
        
        
    }//GEN-LAST:event_searchBTNActionPerformed

    //////////////////////////// Student Report //////////////////////////////////////
    
    private void intoResult(){
        
        String STIID = resuID.getText();
        String resuSTName = resuName.getText();
        String mathRe = mtc.getText();
        String scRe = sc.getText();
        String engRe = eng.getText();
        String sstRe = sst.getText();
        
        //// Codefor the grading
        
        int totalMarks,totalAgg;
        int numEn = 0;
        int numMa = 0;
        int numSc = 0;
        int numSst = 0;
        
        double avere;
        String cEn = "";
        String cMa = "";
        String cSc = "";
        String cSst = "";
       
        
        int tmath = Integer.parseInt(mtc.getText()) ;
        int sci = Integer.parseInt(sc.getText()) ;
        int teng = Integer.parseInt(eng.getText()) ;
        int tss = Integer.parseInt(sst.getText()) ;
       
        /// Total Marks
        totalMarks = tmath+sci+teng+tss;
        
        String stringTotal = Double.toString(totalMarks);
        
        /// Average Marks
        avere = totalMarks/4;
        
        /// aggregates
        
        /// switch for Math
        switch(tmath){
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
              numMa = 1;  
              cMa = "D1";
              break;
              
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
              numMa = 2;  
              cMa = "D2";
              break;
              
             case 65:
            case 66:
            case 67:
            case 68:
            case 69:
              numMa = 3;  
              cMa = "C3"; 
              break;
              
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
              numMa = 4;  
              cMa = "C4";
              break;
              
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
              numMa = 5;  
              cMa = "C5"; 
              break;
              
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
              numMa = 6;  
              cMa = "C6";
              break;
              
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
              numMa = 7;  
              cMa = "P7"; 
            break;
            
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
              numMa = 8;  
              cMa = "P8";
              break;
              
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            numMa = 9;  
            cMa = "F9";
            break;
            
            default:
             
        }
        
        
        /// switch for science
        switch(sci){
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
              numSc = 1;  
              cSc = "D1";
              break;
              
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
              numSc = 2;  
              cSc = "D2";
              break;
              
             case 65:
            case 66:
            case 67:
            case 68:
            case 69:
              numSc = 3;  
              cSc = "C3"; 
              break;
              
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
              numSc = 4;  
              cSc = "C4";
              break;
              
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
              numSc = 5;  
              cSc = "C5"; 
              break;
              
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
              numSc = 6;  
              cSc = "C6";
              break;
              
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
              numSc = 7;  
              cSc = "P7"; 
            break;
            
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
              numSc = 8;  
              cSc = "P8";
              break;
              
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            numSc = 9;  
            cSc = "F9";
            break;
            
            default:
             
        }
        
        
        /// switch for English
        switch(teng){
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
              numEn = 1;  
              cEn = "D1";
              break;
              
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
              numEn = 2;  
              cEn = "D2";
              break;
              
             case 65:
            case 66:
            case 67:
            case 68:
            case 69:
              numEn = 3;  
              cEn = "C3"; 
              break;
              
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
              numEn = 4;  
              cEn = "C4";
              break;
              
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
              numEn = 5;  
              cEn = "C5"; 
              break;
              
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
              numEn = 6;  
              cEn = "C6";
              break;
              
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
              numEn = 7;  
              cEn = "P7"; 
            break;
            
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
              numEn = 8;  
              cEn = "P8";
              break;
              
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            numEn = 9;  
            cEn = "F9";
            break;
            
            default:
             
        }
        
        
        
        /// switch for SST
        switch(tss){
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
              numSst = 1;  
              cSst = "D1";
              break;
              
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
              numSst = 2;  
              cSst = "D2";
              break;
              
             case 65:
            case 66:
            case 67:
            case 68:
            case 69:
              numSst = 3;  
              cSst = "C3"; 
              break;
              
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
              numSst = 4;  
              cSst = "C4";
              break;
              
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
              numSst = 5;  
              cSst = "C5"; 
              break;
              
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
              numSst = 6;  
              cSst = "C6";
              break;
              
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
              numSst = 7;  
              cSst = "P7"; 
            break;
            
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
              numSst = 8;  
              cSst = "P8";
              break;
              
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            numSst = 9;  
            cSst = "F9";
            break;
            
            default:
             
        }
        
        
     
       
        totalAgg = numEn + numMa + numSc + numSst;
        String division = "";
        
        if(totalAgg > 0 && totalAgg < 13){
            division = "One";
            
        }else if(totalAgg > 12 && totalAgg < 25){
            division = "Two";
        }else if(totalAgg > 24 && totalAgg < 35 ){
            division  = "Three";
        }else if(totalAgg > 34 && totalAgg < 45){
            division  = "Four";
        }else{
            division  = "Failed And Useless Kid!!";
        }
        
        
        
         String stringAverage = Double.toString(avere);
        String stringTotalAgg = Integer.toString(totalAgg);
         //// End Code for the grading
         String reqClass = reeclas.getSelectedItem().toString();
         
         connection();
        try {
            pst = conn.prepareStatement("insert into results(ridd,rname,math,grade,science,sgrade,eng,egrade,sst,ssgrade,totaMark,average,aggre,Division,classR) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, STIID);
            pst.setString(2, resuSTName);
            pst.setString(3, mathRe);
            pst.setString(4, cMa);
            pst.setString(5, scRe);
            pst.setString(6, cSc);
            pst.setString(7, engRe);
            pst.setString(8, cEn);
            pst.setString(9, sstRe);
            pst.setString(10, cSst);
            pst.setString(11, stringTotal);
            pst.setString(12, stringAverage);
            pst.setString(13, stringTotalAgg);
            pst.setString(14, division);
            pst.setString(15, reqClass);
            
            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "Student Report Information inserted Successfully!!");
            
            
            resuID.setText("");
            resuID.requestFocus();
            resuName.setText("");
            mtc.setText("");
            sc.setText("");
            eng.setText("");
            sst.setText("");
            
            displayResult();
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    }
    
    
    public void searchStudentResult(){
        
        
    
        
         String seID = resuID.getText();
       
        
        
        
        try {
            
            ps = conn.prepareStatement("Select * from results where ridd=? ");
            ps.setString(1, seID);
            rss = pst.executeQuery();
           
            if(rss.next()){
                
            pst = conn.prepareStatement("Select * from results where ridd=? ");
            pst.setString(1, seID);
            rs = pst.executeQuery();
            
         
                ResultSetMetaData Rsm = rs.getMetaData();
            int cc;
            cc = Rsm.getColumnCount();
            
            DefaultTableModel dff = (DefaultTableModel)resultTable.getModel();
            dff.setRowCount(0);
            
            while(rs.next()){
                
                Vector v5 = new Vector();
                
                for(int i = 1; i <= cc; i++)
                {
        v5.add(rs.getString("ridd"));
        v5.add(rs.getString("rname"));
        v5.add(rs.getString("classR"));
        v5.add(rs.getString("math"));
        v5.add(rs.getString("science"));
        v5.add(rs.getString("eng"));
        v5.add(rs.getString("sst"));
        v5.add(rs.getString("totaMark"));
        v5.add(rs.getString("average"));
        v5.add(rs.getString("aggre"));
       
                
                }
               
                dff.addRow(v5);
                
            }
          
                
                
            }
       
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    private void displayResult(){
        
          connection();
         try {
            pst = conn.prepareStatement("Select * from results ");
            rs = pst.executeQuery();
            ResultSetMetaData Rsm = rs.getMetaData();
            
            int c;
            c = Rsm.getColumnCount();
            
            DefaultTableModel df = (DefaultTableModel)resultTable.getModel();
            df.setRowCount(0);
            
            while(rs.next()){
                
               Vector vv = new Vector();
                
                for(int i = 1; i <= c; i++)
                {
                vv.add(rs.getString("ridd"));
                vv.add(rs.getString("rname"));
                vv.add(rs.getString("classR"));
                vv.add(rs.getString("math"));
                vv.add(rs.getString("science"));
                vv.add(rs.getString("eng"));
                vv.add(rs.getString("sst"));
                vv.add(rs.getString("totaMark"));
                vv.add(rs.getString("average"));
                vv.add(rs.getString("aggre"));
                
                
                }
                
                df.addRow(vv);
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    private void printReportCard(){
        
        
        try {
            String searchID = resuID.getText();
            
            String ssid = "" ;
            connection();
            
            
            ps1 = conn.prepareStatement("Select * from results where ridd=? ");
            ps1.setString(1, searchID);
            rs1 = ps1.executeQuery();
            
            if(rs1.next()){
                ssid = rs1.getString(1);
            }
            
            
            
            
            HashMap hah = new HashMap();
            hah.put("invo", ssid);
            
            // C:\Users\ADMIN\Desktop\java_work\Projects\EdgerPrimarySchool\src\eps\receipt.jrxml
            readFeeTable();
            JasperDesign jd2;
            try {
                jd2 = JRXmlLoader.load("C:\\Users\\Dell\\Desktop\\java work\\Projects\\EdgerPrimarySchool\\src\\eps\\studentResult.jrxml");
                JasperReport jsrr = JasperCompileManager.compileReport(jd2);
            JasperPrint jprpt = JasperFillManager.fillReport(jsrr, hah, conn);
            JasperViewer.viewReport(jprpt);
            
            
            } catch (JRException ex) {
                Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
        
        
    }
    
    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        intoResult();
    }//GEN-LAST:event_submitActionPerformed

    private void reportPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportPrintActionPerformed
       printReportCard();
    }//GEN-LAST:event_reportPrintActionPerformed

    private void reportPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportPrint1ActionPerformed
        
       searchStudentResult();
        reportPrint.setEnabled(true);
    }//GEN-LAST:event_reportPrint1ActionPerformed

    private void homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeMouseClicked
       setColor(home);
       resetColor(addAccount);
       resetColor(registerStudent);
       resetColor(insertResults);
       resetColor(accountsCorner);
      
       
        if(ustype.equals("Admin"))
        {
            mother_pane.setSelectedIndex(4);
        }else if(ustype.equals("addAccount"))
        {
            mother_pane.setSelectedIndex(4);
        }else if(ustype.equals("Secretary"))
        {
            mother_pane.setSelectedIndex(4);
        }else if(ustype.equals("Class Teacher"))
        {
            mother_pane.setSelectedIndex(4);
        }else if(ustype.equals("Accounts"))
        {
            mother_pane.setSelectedIndex(4);
        }
        
       
       
    }//GEN-LAST:event_homeMouseClicked

    private void addAccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addAccountMouseClicked
       
       setColor(addAccount);
       resetColor(home);
       resetColor(registerStudent);
       resetColor(insertResults);
       resetColor(accountsCorner);
       
       
       
     
        if(ustype.equals("Admin"))
        {
           mother_pane.setSelectedIndex(0);
           edit1.setEnabled(false);
           remove1.setEnabled(false);
           create1.setEnabled(true);
        }
        
       readAccTable();
       
       acc_id.setText("");
       aname.setText("");
       aname.requestFocus();
       utype.setSelectedIndex(-1);
       apass.setText("");
       classcomb.setSelectedIndex(-1);
       acont.setText("");
        
    }//GEN-LAST:event_addAccountMouseClicked

    private void registerStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerStudentMouseClicked
       setColor(registerStudent);
       resetColor(addAccount);
       resetColor(home);
       resetColor(insertResults);
       resetColor(accountsCorner);
       
      
        if(ustype.equals("Secretary"))
        {
           mother_pane.setSelectedIndex(1);
           edit_st.setEnabled(false);
           remove_st.setEnabled(false);
           add_st.setEnabled(true);
        }
       stName.setText("");
       stName.requestFocus();
       parentN.setText(""); 
       perCont.setText("");
       classSt.setSelectedIndex(0);
      studentRegAuto();
       readStudentTable();
    }//GEN-LAST:event_registerStudentMouseClicked

    private void insertResultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_insertResultsMouseClicked
       setColor(insertResults);
       resetColor(addAccount);
       resetColor(home);
       resetColor(registerStudent);
       resetColor(accountsCorner);
        
        
        
         if(ustype.equals("Class Teacher"))
        {
           mother_pane.setSelectedIndex(2);
           displayResult();
           reportPrint.setEnabled(false);
        }
        
    }//GEN-LAST:event_insertResultsMouseClicked

    private void accountsCornerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountsCornerMouseClicked
      setColor(accountsCorner);
       resetColor(addAccount);
       resetColor(home);
       resetColor(insertResults);
       resetColor(registerStudent);
        
        
         
         if(ustype.equals("Accounts"))
        {
           mother_pane.setSelectedIndex(3);
        readFeeTable();
        paidBTN.setEnabled(false);
        }
    }//GEN-LAST:event_accountsCornerMouseClicked

    private void logoutMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseMoved
       setColor(logout);
       
    }//GEN-LAST:event_logoutMouseMoved

    private void navigationPaneMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navigationPaneMouseMoved
       
        
       hoveroff(logout);
        /*
       hoveroff(addAccount);
       hoveroff(home);
       hoveroff(insertResults);
       hoveroff(registerStudent);
        
       */
    }//GEN-LAST:event_navigationPaneMouseMoved

    private void addAccountMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addAccountMouseMoved
       //hover(addAccount);
       
        if(ustype.equals("Accounts"))
        {
           hoveroff(registerStudent);
           hoveroff(insertResults);
           hoveroff(accountsCorner);
           hover(addAccount);
        }
       
    }//GEN-LAST:event_addAccountMouseMoved

    private void registerStudentMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerStudentMouseMoved
     //  hover(registerStudent);
    }//GEN-LAST:event_registerStudentMouseMoved

    private void insertResultsMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_insertResultsMouseMoved
        // hover(insertResults);
    }//GEN-LAST:event_insertResultsMouseMoved

    private void accountsCornerMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountsCornerMouseMoved
      // hover(accountsCorner);
    }//GEN-LAST:event_accountsCornerMouseMoved

    private void homeMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeMouseMoved
     //  hover(home);
    }//GEN-LAST:event_homeMouseMoved

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
       new login().setVisible(true);
       this.dispose();
    }//GEN-LAST:event_logoutMouseClicked

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField acc_id;
    private javax.swing.JPanel account;
    private javax.swing.JPanel accountsCorner;
    private javax.swing.JTable accounttbl;
    private javax.swing.JTextField acont;
    private javax.swing.JPanel addAccount;
    private javax.swing.JButton add_st;
    private javax.swing.JTextField aname;
    private javax.swing.JPasswordField apass;
    private javax.swing.JPanel bursa;
    private javax.swing.JComboBox<String> classSt;
    private javax.swing.JComboBox<String> classcomb;
    private javax.swing.JButton create1;
    private javax.swing.JPanel cteacher;
    private javax.swing.JButton edit1;
    private javax.swing.JButton edit_st;
    private javax.swing.JTextField eng;
    private javax.swing.JTable feetable;
    private javax.swing.JPanel home;
    private javax.swing.JPanel home5;
    private javax.swing.JPanel home6;
    private javax.swing.JPanel homep;
    private javax.swing.JPanel insertResults;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JPanel logout;
    private javax.swing.JPanel mainpane;
    private javax.swing.JTabbedPane mother_pane;
    private javax.swing.JTextField mtc;
    private javax.swing.JPanel navigationPane;
    private javax.swing.JTextField ntuition;
    private javax.swing.JButton paidBTN;
    private javax.swing.JTextField parentN;
    private javax.swing.JTextField perCont;
    private javax.swing.JComboBox<String> reeclas;
    private javax.swing.JPanel registerStudent;
    private javax.swing.JButton remove1;
    private javax.swing.JButton remove_st;
    private javax.swing.JButton reportPrint;
    private javax.swing.JButton reportPrint1;
    private javax.swing.JTextField resuID;
    private javax.swing.JTextField resuName;
    private javax.swing.JTable resultTable;
    private javax.swing.JTextField sc;
    private javax.swing.JButton searchBTN;
    private javax.swing.JPanel secretary;
    private javax.swing.JTextField sst;
    private javax.swing.JLabel stAID;
    private javax.swing.JComboBox<String> stClF;
    private javax.swing.JTextField stName;
    private javax.swing.JTextField stnameF;
    private javax.swing.JTable stuentTable;
    private javax.swing.JButton submit;
    private javax.swing.JComboBox<String> utype;
    // End of variables declaration//GEN-END:variables
}
