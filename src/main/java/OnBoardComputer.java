import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnBoardComputer implements BurnStream {
    //phase 1: make alt % 1000 == 501; //(BURN ALIGNER)
        //second number in sequence should control the tens' and ones' places (the difference of number and 100)
        //and third number should immediately go back to the original
    //phase 2: burn off thousands place of speed to 5501 (if needed) (DESCENDER)
    //phase 3: 10 consecutive bursts of 200 burn (SPEED CUTTER)
    //phase 4: 1 burn of 99
    //aircraft from this height will always successfully land after 100 seconds
    public static final int MAGIC_HEIGHT = 4501;
    //after completing phase 3; aircraft will then execute a burn of BURN_FINALE and will land safely everytime
    public static final int BURN_FINALE = 99;
    public List<Integer> burns;
    public int Index = -1;
    public int Phase = 0;
    @Override
    public int getNextBurn(DescentEvent status) {
        if(Phase == 0){
            this.burns = this.burnAligner(status.getAltitude());
            Phase++;
        }
        if(Phase == 1){
            if(Index < burns.size() - 1){
                Index++;
                System.out.println(burns.get(Index));
                return burns.get(Index);
            }
            Phase++;
            Index = -1;
            burns = descender(status.getAltitude());
        }
        if(Phase == 2){
            if(Index < burns.size() - 1){
                Index++;
                System.out.println(burns.get(Index));
                return burns.get(Index);
            }
            Phase++;
            Index = -1;
            burns = speedCutter();
        }
        if(Phase == 3){
            if(Index< burns.size() - 1){
                Index++;
                System.out.println(burns.get(Index));
                return burns.get(Index);
            }
            Phase++;
        }
        if(Phase == 4){
            System.out.println(BURN_FINALE);
            return BURN_FINALE;
        }
        //nothing should ever reach this statement;
        return 0;
    }
    //preconditions 20000>= start >= 10000
    private List<Integer> burnAligner(int start){
        //+- 100
        //init starting velo 1000
        //last height must reach 1000 within one turn
        //start - MAGIC_HEIGHT
        start-=5501;
        List<Integer> hundalign = new ArrayList<Integer>();
        //tens place
        int tens = start % 100;
        switch((start % 1000) / 100){
            case 1:
                hundalign.addAll(Arrays.asList(100,0,200));
                break;
            case 2:
                hundalign.addAll(Arrays.asList(100,0,100,200));
                break;
            case 3:
                hundalign.addAll(Arrays.asList(100,0,100,100,200));
                break;
            case 4:
                hundalign.addAll(Arrays.asList(100,0,0,200,200));
                break;
            case 5:
                hundalign.addAll(Arrays.asList(200,100,100,100,100,0));
                break;
            case 6:
                hundalign.addAll(Arrays.asList(200,100,100,100,0));
                break;
            case 7:
                hundalign.addAll(Arrays.asList(200,100,100,0));
                break;
            case 8: case 9:
                hundalign.addAll(Arrays.asList(200,100,0));
                break;
            default:
                hundalign.addAll(Arrays.asList(100,100));
        }
        hundalign.set(0,burnAdder(hundalign.get(0),tens));
        hundalign.set(1,burnAdder(hundalign.get(1),-tens));
        return hundalign;
    }
    private int burnAdder(int in,int addend){
        //if 200; 200,99 == 101; 900 -> 999
        //if 100; 100,99 == 1; 1000 -> 1099
        in = (-in) + 100 + addend;
        return (-in) + 100;
    }
    //precondition leftToGo will always be divisible by 1000
    private List<Integer> descender(int height){
        int leftToGo = height - MAGIC_HEIGHT;
        List<Integer> descender = new ArrayList<>();
        while(leftToGo > 0){
            descender.add(100);
            leftToGo-=1000;
        }
        return descender;
    }
    private List<Integer> speedCutter(){
        List<Integer> speedCutter = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            speedCutter.add(200);
        }
        return speedCutter;
    }
}
