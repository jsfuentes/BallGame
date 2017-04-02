/**
 * Created by Admin on 4/1/2017.
 */

public class Particle {
    public float mVelX, mVelY, mPosX, mPosY;
    private static final float COR = 0.7f;

    public void updatePosition(float sx, float sy, float sz, long timestamp){

        float dt = (System.nanoTime() - timestamp)/1000000000000.0f;
        mVelX +=-sx * dt;
        mVelY += -sy * dt;

        mPosX += mVelX * dt;
        mPosY += mVelY * dt;
    }

    public void resolveCollisionWithBounds(float mHorizontalBound, float mVerticalBound){
        //bound handling
        if(mPosX > mHorizontalBound){
            mPosX = mHorizontalBound;
            mVelX = -mVelX * COR;
        } else if (mPosX<-mHorizontalBound){
            mPosX = -mHorizontalBound;
            mVelX = -mVelX * COR;
        }
        if(mPosY > mVerticalBound) {
            mPosY = mVerticalBound;
            mVelY = -mVelY * COR;
        } else if (mPosY < mVerticalBound) {
            mPosY = -mVerticalBound;
            mVelY = -mVelY * COR;
        }
    }
}
