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
    @Override
    public int getNextBurn(DescentEvent status) {

        return 0;
    }
    //preconditions 20000>= start >= 10000
    public static List<Integer> burnArray(int start){
        //+- 100
        //init starting velo 1000
        //last height must reach 1000 within one turn
        start-=MAGIC_HEIGHT;
        List<Integer> hundalign = new ArrayList<Integer>();
        //tens place
        int tens = start % 100;
        int hundreds = (start % 1000)/100;
        switch((start % 1000) / 100){
            //200 == -100
            //100 == 0
            //0 == +100
            case 1:
                hundalign.addAll(Arrays.asList(1100,1000,1000));
                break;
            case 2:
                hundalign.addAll(Arrays.asList(1100,1100,1000,1000));
                break;
            case 3:
                hundalign.addAll(Arrays.asList(1100,1100,1100));
                break;
            case 4:
                hundalign.addAll(Arrays.asList(1100,1100,1100,1100));
                break;
            case 5:
                hundalign.addAll(Arrays.asList(1100,1200,1100,1100));
                break;
            case 6:
                hundalign.addAll(Arrays.asList(900,900,900,900));
                break;
            case 7:
                hundalign.addAll(Arrays.asList(900,900,900));
                break;
            case 8: case 9:
                hundalign.addAll(Arrays.asList(900,900));
                break;
            default:
                hundalign.addAll(Arrays.asList(1000,1000));
        }
        hundalign.set(hundalign.size() - 2,hundalign.get(hundalign.size() -2) + tens);
        return hundalign;
    }

}
