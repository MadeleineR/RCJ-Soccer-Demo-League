public class GUIThread extends Thread{
    
    private GUIJFrame gui;
    
    public GUIThread(GUIJFrame gui){
        this.gui = gui;
    }
    
    @Override
    public void run(){
        gui.setVisible(true);
    }
}