package Course;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;
/**
 *Course will just store all the data retrieved from the database, so the program doesn't
 *have to consistently communicate with the database.
 */
public class Course{
    /*All instance variables are public and final
      except lecture because we want to be able to change
      the lecture time, professor, etc.
      For section: profesor will represent TA
     */
    public final String courseID;
    public final String title;
    public final String fullTitle;
    public final String dept;
    public final String units;
    public final Course[] preReqs;
    public final String[] restrictions;
    public final String[] geFulfill;
    private Lecture lectInfo;
    private Lecture sectInfo;
    private JButton view;
    
	/**
		*@param courseID String of courseID according to the UCSB course catalog
		*@param title String of shorter title of the course e.g. CMPSC 8
		*@param fullTitle String of full title of course e.g. Computer Science Project Course
		*@param dept String of department that the course belongs to
		*@param units Integer amount of units the course counts for
		*@param preReqs String array of prerequisites for the course
		*@param restrictions String array of restrictions of the course
		*@param String array of the G.E. requirements that this course fulfills
		*@param Lecture object that contains information about the course lecture
		*@param Lecture object that contains information about the course section
		*/
    
    public Course(String courseID, String title, String fullTitle, String dept, String units,
                  Course[] preReqs, String[] restrictions, String[] geFulfill, Lecture lectInfo,
                  Lecture sectInfo){
        this.courseID = courseID;
        this.title = title;
        this.fullTitle = fullTitle;
        this.dept = dept;
        this.units = units;
        this.preReqs = preReqs;
        this.restrictions = restrictions;
        this.geFulfill = geFulfill;
        this.lectInfo = lectInfo;
        this.sectInfo = sectInfo;
    }
    //Methods for only private member variable
    public Lecture getLect(){
        return this.lectInfo;
    }
    public void setLect(Lecture lectInfo){
        this.lectInfo = lectInfo;
    }
    public Lecture getSect(){
        return this.sectInfo;
    }
    public void setSect(Lecture sectInfo){
        this.sectInfo = sectInfo;
    }
    
	/**
		* sets the color of the course as seen when the user views his/her schedule
		*@param c Desired color to use for the course
		*/
    public void setColor(Color c){
        lectInfo.col = c;
        sectInfo.col = c;
    }
    public JButton getView(){
        return this.view;
    }
    /**
     @return a panel that displays the course information
     */
    public JPanel getPanel(){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(500, 600));
        Color myColor = this.lectInfo.col;
        int countRows = 8;
        countRows+=3;
        panel.setLayout(new GridLayout(countRows,2));
        JPanel[][] panelHolder = new JPanel[countRows][2];
        for(int m = 0; m<countRows; m++){
            for(int n= 0; n<2; n++){
                panelHolder[m][n] = new JPanel();
                panelHolder[m][n].setLayout(new BorderLayout());
                panel.add(panelHolder[m][n]);
                panelHolder[m][n].setBackground(myColor);
            }
        }
        
        JLabel t = new JLabel("Title: ");
        JLabel ft = new JLabel("Full Title: ");
        JLabel u = new JLabel("Units: ");
        JLabel d = new JLabel("Department: ");
        JLabel ti = new JLabel("Time: ");
        JLabel dayList = new JLabel("Days: ");
        JLabel l = new JLabel("Location: ");
        JLabel sectTime = new JLabel("Section Time: ");
        JLabel sectLoc = new JLabel("Section Location: ");
        Font font = t.getFont();
        Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
        t.setFont(boldFont);
        ft.setFont(boldFont);
        u.setFont(boldFont);
        d.setFont(boldFont);
        ti.setFont(boldFont);
        dayList.setFont(boldFont);
        l.setFont(boldFont);
        sectTime.setFont(boldFont);
        sectLoc.setFont(boldFont);
        
        
        String myUnits = "" + this.units;
        
        JLabel code = new JLabel(this.courseID);
        JLabel title = new JLabel(this.title);
        JLabel fullTitle = new JLabel(this.fullTitle);
        JLabel units =  new JLabel(myUnits);
        JLabel department =  new JLabel(this.dept);
        JLabel time = new JLabel(this.lectInfo.timeStartString() + " - " + this.lectInfo.timeEndString());
        JLabel day = new JLabel(this.lectInfo.dayString());
        JLabel location = new JLabel(this.lectInfo.location);
        JLabel sTime = new JLabel(this.sectInfo.timeStartString()+ " - " + this.sectInfo.timeEndString());
        JLabel sLoc = new JLabel(this.sectInfo.location);

        
        panelHolder[0][0].add(t, BorderLayout.EAST);
        panelHolder[0][1].add(title, BorderLayout.WEST);
        panelHolder[1][0].add(ft, BorderLayout.EAST);
        panelHolder[1][1].add(fullTitle, BorderLayout.WEST);
        panelHolder[2][0].add(u, BorderLayout.EAST);
        panelHolder[2][1].add(units, BorderLayout.WEST);
        panelHolder[3][0].add(d, BorderLayout.EAST);
        panelHolder[3][1].add(department, BorderLayout.WEST);
        panelHolder[4][0].add(ti, BorderLayout.EAST);
        panelHolder[4][1].add(time, BorderLayout.WEST);
        panelHolder[5][0].add(dayList, BorderLayout.EAST);
        panelHolder[5][1].add(day, BorderLayout.WEST);
        //panelHolder[6][0].add(l, BorderLayout.EAST);
        //panelHolder[6][1].add(location, BorderLayout.WEST);
        panelHolder[6][0].add(sectTime, BorderLayout.EAST);
        panelHolder[6][1].add(sTime, BorderLayout.WEST);
        //panelHolder[7][0].add(sectLoc, BorderLayout.EAST);
        //panelHolder[7][1].add(sLoc, BorderLayout.WEST);
        
        
        JLabel p = new JLabel("PreRequisites: ");
        JLabel g = new JLabel("May apply to GE Requirements: ");
        JLabel r = new JLabel("Restrictions: ");
        p.setFont(boldFont);
        g.setFont(boldFont);
        r.setFont(boldFont);
        
        int newCount = 7;
        JLabel temp;
        panelHolder[newCount][0].add(p, BorderLayout.EAST);
        panelHolder[newCount][0].setBackground(myColor);
        if(this.preReqs.length==0){
            temp = new JLabel("None");
            panelHolder[newCount][1].add(temp, BorderLayout.WEST);
            panelHolder[newCount][1].setBackground(myColor);
            newCount++;
        }
        else{
            for(Course c:this.preReqs){
                temp = new JLabel(c.courseID);
                panelHolder[newCount][1].add(temp, BorderLayout.WEST);
                panelHolder[newCount][1].setBackground(myColor);
                newCount++;
            }
        }
        panelHolder[newCount][0].add(g, BorderLayout.EAST);
        panelHolder[newCount][0].setBackground(myColor);
        if(this.geFulfill.length==0){
            temp = new JLabel("None");
            panelHolder[newCount][1].add(temp, BorderLayout.WEST);
            panelHolder[newCount][1].setBackground(myColor);
            newCount++;
        }
        else{
            for(String s:this.geFulfill){
                temp = new JLabel(s);
                panelHolder[newCount][1].add(temp, BorderLayout.WEST);
                panelHolder[newCount][1].setBackground(myColor);
                newCount++;
            }
        }
        panelHolder[newCount][0].add(r, BorderLayout.EAST);
        panelHolder[newCount][0].setBackground(myColor);
        if(this.restrictions.length==0){
            temp = new JLabel("None");
            panelHolder[newCount][1].add(temp, BorderLayout.WEST);
            panelHolder[newCount][1].setBackground(myColor);
            newCount++;
        }
        else{
            for(String s:this.restrictions){
                temp = new JLabel(s);
                panelHolder[newCount][1].add(temp, BorderLayout.WEST);
                panelHolder[newCount][1].setBackground(myColor);
                newCount++;
            }
        }
        return panel;
    }
    
}

