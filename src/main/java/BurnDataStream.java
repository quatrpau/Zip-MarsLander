public class BurnDataStream implements BurnStream {
    // these are the series of burns made each 10 secs by the lander.
    // change them to see if you can get the lander to make a soft landing.
    // burns are between 0 and 200. This burn array usually crashes.

    //for init ht of 10000
    //starting sequence: 0(ie +100ie 1100);  1(ie +99ie 1199) ; 199(ie -99ie 1100); 100(ie 0; ie 1100); 200(ie -100; ie 1000)
    //speed drop to 0(must start at 4501 meters and with 1000 velo) 200 * 10
    //finally one last 99 to bridge the gap to the ground
    //10000
    int burnArray[] = {1, 99,0, 200, 200, 200,200, 200, 200, 200, 200, 200, 200, 200, 200,99};
    //20000
    //int burnArray[] = {0, 1,199, 100, 200,100,100,100,100,100,100,100,100,100,100, 200,200, 200, 200, 200, 200, 200, 200, 200, 200,99};
    //5501 = magic number or 4501 if 1000 has already been subtracted
    //int burnArray[] = {200,200,200,200,200,200,200,200,200,200};
    int burnIdx = -1;
    public BurnDataStream() { }
    public BurnDataStream(int[] burns) {
        this.burnArray = burns;
    }
    @Override
    public int getNextBurn(DescentEvent status) {
        if (burnIdx < burnArray.length) {
            burnIdx++;
            System.out.println(burnArray[burnIdx]); /*hack!*/
            return burnArray[burnIdx];
        }
        return 0;
    }
}
