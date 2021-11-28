import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnBoardComputer implements BurnStream {
    //phase 1: make alt % 1000 == 501;
        //second number in sequence should control the tens' and ones' places (the difference of number and 100)
        //and third number should immediately go back to the original
    //phase 2: burn off thousands place of speed to 5501 (if needed)
    //phase 3: 10 consecutive bursts of 200 burn
    //phase 4: 1 burn of 99
    //aircraft from this height will always successfully land after 110 seconds
    public static final int MAGIC_HEIGHT = 5501;
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
                return burns.get(Index);
            }
            Phase++;
            Index = -1;
        }
        return 0;
    }
    //preconditions 20000>= start >= 10000
    public List<Integer> burnAligner(int start){
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
                hundalign.addAll(Arrays.asList(100,0));
                break;
            case 2:
                hundalign.addAll(Arrays.asList(100,0,100));
                break;
            case 3:
                hundalign.addAll(Arrays.asList(100,0,100,100));
                break;
            case 4:
                hundalign.addAll(Arrays.asList(100,0,0,200));
                break;
            case 5:
                hundalign.addAll(Arrays.asList(200,100,100,100,100));
                break;
            case 6:
                hundalign.addAll(Arrays.asList(200,100,100,100));
                break;
            case 7:
                hundalign.addAll(Arrays.asList(200,100,100));
                break;
            case 8: case 9:
                hundalign.addAll(Arrays.asList(200,100));
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
}
