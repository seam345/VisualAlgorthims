package uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.bubbleSort;

import android.graphics.Color;
import android.os.Handler;

import uk.co.cyborgapps.visualalgorithms.Constants;
import uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.RainbowSetup;

//import android.util.Log;

/**
 * Created by sean on 6/12/15.
 */
public class RainbowBubbleThread extends Thread implements Constants
{
	int sleepTime = 30;
	private int arraySize;
	//	private static final  String TAG = "seanborg";
	private final Handler mHandler;
	int maxX, maxY;

	double rainbowNumber;
	public int[] array;
	public int[] placehold;
	public int[] colour;


	public RainbowBubbleThread(Handler handler, int X, int Y, int size, int sleep, int tempRainbowNumber)
	{
		mHandler = handler;
		maxX = X;
		maxY = Y;
		sleepTime = sleep;
		arraySize = size;
		colour = new int[arraySize];
		placehold = new int[arraySize];
		array = new int[arraySize];
		rainbowNumber = tempRainbowNumber;
		RainbowSetup rainbowSetup = new RainbowSetup(maxX, maxY, arraySize, (int) rainbowNumber);
		array = rainbowSetup.setupRainbowArray();
		colour = rainbowSetup.getColour();
	}

	@Override
	public void run()
	{
		mHandler.obtainMessage(3, colour).sendToTarget();
		mHandler.obtainMessage(0, array).sendToTarget();
		try
		{
			Thread.sleep(100);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		long startTime = System.nanoTime();

		bubblerRainowSort();

		long endTime = System.nanoTime();
		float f = (float) ((endTime - startTime) * 0.000000001);
		mHandler.obtainMessage(4, f).sendToTarget();

	}

	private void bubblerRainowSort()
	{
		boolean sorted = false;
		int size = arraySize;

		while (!sorted)
		{
			sorted = true;
			for (int i = 0; i < (size - 1); i++)
			{
				if (sleepTime != 0)
				{
					if (i == 0)
					{
						mHandler.obtainMessage(1, i, Color.BLACK, array).sendToTarget();
						mHandler.obtainMessage(2).sendToTarget();
					} else
					{
						mHandler.obtainMessage(1, i - 1, colour[i - 1], array).sendToTarget();
						mHandler.obtainMessage(1, i, Color.BLACK, array).sendToTarget();
						mHandler.obtainMessage(2).sendToTarget();
					}

					try
					{
						Thread.sleep(sleepTime);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
						return;
					}
				}

				if (array[i] < array[i + 1])
				{
					int temp;
					temp = array[i];
					array[i] = array[i + 1];
					array[i + 1] = temp;

					temp = colour[i];
					colour[i] = colour[i + 1];
					colour[i + 1] = temp;

					sorted = false;

					if (sleepTime != 0)
					{
						mHandler.obtainMessage(1, i + 1, Color.BLACK, array).sendToTarget();
						mHandler.obtainMessage(1, i, Color.BLACK, array).sendToTarget();
						mHandler.obtainMessage(2).sendToTarget();

						//Log.i(TAG,"message sent, enter sleep");
						try
						{
							Thread.sleep(sleepTime);
						} catch (InterruptedException e)
						{
							e.printStackTrace();
							return;
						}
					}
				}
			}
			size = size - 1;


			if (sleepTime != 0)
			{
				mHandler.obtainMessage(1, size, colour[size], array).sendToTarget();
				mHandler.obtainMessage(1, size - 1, colour[size - 1], array).sendToTarget();
				mHandler.obtainMessage(2).sendToTarget();
				try
				{
					Thread.sleep(100);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
					return;
				}
			}
		}
	}
}
