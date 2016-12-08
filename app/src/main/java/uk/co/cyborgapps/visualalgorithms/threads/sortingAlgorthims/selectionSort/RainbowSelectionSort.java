package uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.selectionSort;

import android.graphics.Color;
import android.os.Handler;

import uk.co.cyborgapps.visualalgorithms.Constants;
import uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.RainbowSetup;

/**
 * Created by sean on 7/10/15 10:19 PM pecific coast time.
 */
public class RainbowSelectionSort extends Thread implements Constants
{
	private int sleepTime;
	private int arraySize;
	//	private static final  String TAG = "seanborg";
	private final Handler mHandler;
	private int maxX, maxY;
	private int[] array;
	private int[] colour;
	private double rainbowNumber;

	public RainbowSelectionSort(Handler handler, int X, int Y, int size, int sleep, int tempRainbowNumber)
	{
		mHandler = handler;
		maxX =X;
		maxY = Y;
		sleepTime = sleep;
		arraySize =  size;
		colour = new int[arraySize];
		array = new int[arraySize];
		rainbowNumber = tempRainbowNumber;
		RainbowSetup rainbowSetup = new RainbowSetup(maxX,maxY,arraySize,(int) rainbowNumber);
		array = rainbowSetup.setupRainbowArray();
		colour = rainbowSetup.getColour();

	}

	@Override
	public void run()
	{
		mHandler.obtainMessage(3, colour).sendToTarget();		//only needs to be sent once as its past by refrance and the handerler saves it to a veriable todo maybe work out a more robust way
		mHandler.obtainMessage(0, array).sendToTarget();

		try
		{
			Thread.sleep(100);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
			return;
		}

		long startTime = System.nanoTime();

		selectionRainbowSort();

		long endTime = System.nanoTime();
		float f = (float) ((endTime - startTime) * 0.000000001);
		mHandler.obtainMessage(4, f).sendToTarget();

		mHandler.obtainMessage(0, array).sendToTarget();
	}

	private void selectionRainbowSort ()
	{
		int  min;

		for (int i =0; i< arraySize-1; i++)
		{
			min = i;
			for (int j = i; j < arraySize; j++ )
			{

				if (sleepTime != 0)
				{
					mHandler.obtainMessage(1, j, Color.BLACK, array).sendToTarget();
					if ((min != (j - 1)) && j != 0) mHandler.obtainMessage(1, j - 1, colour[j - 1], array).sendToTarget();
					mHandler.obtainMessage(2).sendToTarget();
					try
					{
						Thread.sleep(sleepTime);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
						return;
					}
				}


				if (array[j] > array[min])
				{
					if (sleepTime != 0)
					{
						mHandler.obtainMessage(1, min, colour[min], array).sendToTarget();
						mHandler.obtainMessage(2).sendToTarget();
						try
						{
							Thread.sleep(sleepTime);
						} catch (InterruptedException e)
						{
							e.printStackTrace();
							return;
						}
					}
					min = j;
				}
			}


			if (min != i)
			{
				int temp = array[min];
				array[min] = array[i];
				array[i] = temp;

				temp = colour[min];
				colour[min] = colour[i];
				colour[i] = temp;
			}
			if (sleepTime != 0)
			{
				mHandler.obtainMessage(1, min, colour[min], array).sendToTarget();
				mHandler.obtainMessage(1, i, colour[i], array).sendToTarget();
				mHandler.obtainMessage(1, arraySize - 1, colour[arraySize - 1], array).sendToTarget();
				mHandler.obtainMessage(2).sendToTarget();
			}

		}

	}

}
