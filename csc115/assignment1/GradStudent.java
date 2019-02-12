/*
* Name: Evan Roubekas
* ID: V0089140
* Date: Sept. 23rd
* Filename: GradStudent.java
* Details: CSC115 Assignment 1
*/

public class GradStudent extends Student
{
    final static int PHD = 1;
    final static int MSC = 2;
    final static int MA = 3;

    private String supervisor;
    private int    program;
    private String PRO;


    // Purpose:
    //  Initialize this instance of GradStudent. Make sure to use
    //  the superclass constructor with an appropriate call
    //  to "super(...)".
    //
    public GradStudent (String id, String name, double credits,
        String supervisor, int program){
          super(id, name, credits);
          this.supervisor = supervisor;
          this.program = program;
          this.PRO = null;
    }


    // Purpose:
    //  Assign the supervisor of this grad student to the
    //  value passed in as a parameter.
    //
    public void setSupervisor(String supervisor) {
      this.supervisor = supervisor;
    }


    // Purpose:
    //  Return the name of the supervisor for this grad student.
    //
    public String getSupervisor()
    {
      return supervisor;
    }


    // Purpose:
    //  Change the program in which the grad student is registered
    //  to that given as a parameter.
    //
    public void setProgram(int program) {
      this.program = program;
    }


    // Purpose:
    //  Return the program in which the grad student is registered.
    //
    public int getProgram()
    {
      return program;
    }


    // Purpose:
    //  Return a String representation of this Grad Student
    //
    // Returns:
    //  id:name:credits:supervisor:program
    //
    // Examples:
    //  GradStudent p = new GradStudent("V0222", "Kristy Clark", 1.5,
    //      PHD, "Dr. Weaver");
    //
    //  p.toString() returns  V0222:Kristy Clark:1.5:Dr. Weaver:PHD
    //
    //  Note: The strings for program are "PHD", "MSC" and "MA".
    //
    public String toString()
    {
      if (program == 1) {
        PRO = "PHD";
      } else if (program == 2) {
        PRO = "MSC";
      } else if (program == 3) {
        PRO = "MA";
      }
      return String.format(getId() + ":" + getName() + ":" + getCredits() + ":" + supervisor + ":" + PRO);
    }
}
