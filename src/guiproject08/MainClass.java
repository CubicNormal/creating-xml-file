
package guiproject08;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

class Employee
{
    private int code;
    private String name;
    private int sal;
    private String dept;
    public Employee()
    {
        code=sal=0;
        name=dept="";
    }
    public Employee(int code,String name,int sal,String dept)
    {
        this.code=code;
        this.name=name;
        this.sal=sal;
        this.dept=dept;
    }
    public int getCode()
    {
        return code;
    }
    public String getName()
    {
        return name;
    }
    public int getSal()
    {
        return sal;
    }
    public String getDept()
    {
        return dept;
    }
            
}
class MainPanel extends JPanel
{
    private JLabel lblCode,lblName,lblSal,lblDept;
    private JTextField txtCode,txtName,txtSal,txtDept;
    private JTextArea txtReport;
    private JScrollPane spnReport;
    private JButton btnSubmit,btnCommit,btnShow,btnExit,btnBack;
    private ArrayList<Employee> empList=new ArrayList<Employee>();
    
    private KeyListener key=new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            char ch=e.getKeyChar();
            Object ob=e.getSource();
            if(ch==KeyEvent.VK_ENTER)
            {
                if(ob==btnSubmit)
                {
                    Employee emp=new Employee(Integer.parseInt(txtCode.getText()),txtName.getText(),Integer.parseInt(txtSal.getText()),txtDept.getText());
                    empList.add(emp);
                    txtCode.setText("");
                    txtName.setText("");
                    txtSal.setText("");
                    txtDept.setText("");
                    txtCode.grabFocus();
                }
                else
                {
                    /*
                    if(e.getSource()==txtCode)
                        txtName.grabFocus();
                    else if(e.getSource()==txtName)
                        txtSal.grabFocus();
                    else if(e.getSource()==txtSal)
                        txtDept.grabFocus();
                    else if(e.getSource()==txtDept)
                        btnSubmit.grabFocus();
                    */
                    ((JTextField)ob).transferFocus();
                    
                }
            }
            else{
                    if(ob==txtCode ||ob==txtSal)
                    {
                        if(ch<'0' || ch>'9')
                            e.setKeyChar((char)0);
                    }
                    else if(ob==txtName || ob==txtDept)
                    {
                        
                         e.setKeyChar(Character.toUpperCase(ch));
                           
                       // ((JTextField)ob).setText(((JTextField)ob).getText().toUpperCase());
                    }
            }
                
        }

        @Override
        public void keyPressed(KeyEvent e) {
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    };
    private JLabel makeLabel(String cap,int x,int y,int w,int h)
    {
        JLabel temp = new JLabel(cap);
        temp.setFont(new Font("Courier New",1,24));
        temp.setBounds(x,y,w,h);
        add(temp);
        return temp;
    }
    private JTextField makeTextField(int x,int y,int w,int h)
    {
        JTextField temp = new JTextField();
        temp.setFont(new Font("Courier New",1,20));
        temp.setHorizontalAlignment(JTextField.CENTER);
        temp.setBounds(x,y,w,h);
        temp.addKeyListener(key);
        add(temp);
        return temp;
    }
    private JButton makeButton(String cap,int x,int y,int w,int h)
    {
        JButton temp = new JButton(cap);
        temp.setFont(new Font("Verdana",1,14));
        temp.setBounds(x,y,w,h);
        temp.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Object ob = e.getSource();
                if(ob==btnSubmit)
                {
                    Employee emp=new Employee(Integer.parseInt(txtCode.getText()),txtName.getText(),Integer.parseInt(txtSal.getText()),txtDept.getText());
                    empList.add(emp);
                    txtCode.setText("");
                    txtName.setText("");
                    txtSal.setText("");
                    txtDept.setText("");
                    txtCode.grabFocus();
                }
                else if(ob == btnCommit)
                {
                    try    
                    {
                        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dbuilder = dbfactory.newDocumentBuilder();
                        Document doc = dbuilder.newDocument();
                        Element root = doc.createElement("employee");
                        doc.appendChild(root);
                        
                        
                        
                        Element emp=doc.createElement("emp");
                        root.appendChild(emp);
                        
                        Element code=doc.createElement("code");
                        code.appendChild(doc.createTextNode("1111"));
                        emp.appendChild(code);
                        
                        Element name=doc.createElement("name");
                        name.appendChild(doc.createTextNode("Ranjit"));
                        emp.appendChild(name);
                        
                        Element sal=doc.createElement("sal");
                        sal.appendChild(doc.createTextNode("69420"));
                        emp.appendChild(sal);
                        
                        
                        Element dept=doc.createElement("dept");
                        dept.appendChild(doc.createTextNode("69420"));
                        emp.appendChild(dept);
                        
                        
                        TransformerFactory transformerfactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerfactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File("employee.xml"));
                        transformer.transform(source, result);
                        JOptionPane.showMessageDialog(null,"XML file created successfully");
                        
                        
                    }
                    catch(Exception ex)
                    {
                        JOptionPane.showMessageDialog(null,ex);
                    }
                }
                else if(ob==btnShow)
                {
                    int slno = 0;
                    for(Employee temp:empList)
                    {
                        String msg = "";
                        msg = String.format("%-20d", temp.getCode())+"|"+String.format("%-20s", temp.getName())+"|"+String.format("%-10d", temp.getSal())+"|"+String.format("%-10s", temp.getDept());
                        txtReport.append(msg);
                    }
                    setVisible();
                }
                else if(ob==btnExit)
                {
                    System.exit(0);
                }
                else if(ob==btnBack)
                {
                    setVisible();
                }
                
            }
        });
        add(temp);
        return temp;
    }
    private void setVisible()
    {
        lblCode.setVisible(!lblCode.isVisible());
        lblName.setVisible(!lblName.isVisible());
        lblSal.setVisible(!lblSal.isVisible());
        lblDept.setVisible(!lblDept.isVisible());
        txtCode.setVisible(!txtCode.isVisible());
        txtName.setVisible(!txtName.isVisible());
        txtSal.setVisible(!txtSal.isVisible());
        txtDept.setVisible(!txtDept.isVisible());
        spnReport.setVisible(!spnReport.isVisible());
        btnSubmit.setVisible(!btnSubmit.isVisible());
        btnCommit.setVisible(!btnCommit.isVisible()); 
        btnShow.setVisible(!btnShow.isVisible()); 
        btnExit.setVisible(!btnExit.isVisible()); 
        btnBack.setVisible(!btnBack.isVisible()); 
    }
    public MainPanel()
    {
        lblCode   = makeLabel("Employee Code",     10, 10,250,30);
        txtCode   = makeTextField(          270, 10,300,30);
        lblName   = makeLabel("Employee Name",     10, 60,250,30);
        txtName   = makeTextField(          270, 60,300,30);
        lblSal    = makeLabel("Basic Salary",      10,110,250,30);
        txtSal    = makeTextField(          270,110,300,30);
        lblDept   = makeLabel("Department Name",   10,160,250,30);
        txtDept   = makeTextField(          270,160,300,30);
        btnSubmit = makeButton("Submit",     40,210,100,30);
        btnSubmit.addKeyListener(key);
        btnCommit = makeButton("Commit",    180,210,100,30);
        btnShow   = makeButton("Show",      320,210,100,30);
        btnExit   = makeButton("Exit",      460,210,100,30);
        
        txtReport = new JTextArea();
        txtReport.setFont(new Font("Courier New",1,14));
        txtReport.setEditable(false);
        spnReport = new JScrollPane(txtReport);
        spnReport.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        spnReport.setBounds(10,10,560,200);
        spnReport.setVisible(false);
        add(spnReport);
        btnBack   = makeButton("Back",      250,220,100,30);
        btnBack.setVisible(false);
    }
}
class MainFrame extends JFrame
{
    private MainPanel panel;
    public MainFrame()
    {
        panel = new MainPanel();
        panel.setBackground(Color.YELLOW);
        panel.setLayout(new BorderLayout());
        super.add(panel);
    }
}
public class MainClass
{
    public static void main(String[] args)
    {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("New User Regestration");
        frame.setSize(600, 300);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
}

