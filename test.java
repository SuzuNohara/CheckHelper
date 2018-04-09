public class test{
    public static void main(String args[]){
        int range = (Integer.parseInt(args[0]) - Integer.parseInt(args[1])) + 1;
        for(int i = 0; i < Integer.parseInt(args[2]); i++){
            System.out.println((int)(Math.random() * range) + Integer.parseInt(args[1]));
        }
    }
}