package uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.insertionSort;

import android.graphics.Color;
import android.os.Handler;

import uk.co.cyborgapps.visualalgorithms.Constants;
import uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.RainbowSetup;

/**
 * Created by sean on 7/10/15.
 */
public class RainbowInsertionSort extends Thread implements Constants
{
	private int sleepTime;
	private int arraySize;
	//	private static final  String TAG = "seanborg";
	private final Handler mHandler;
	int maxX, maxY;

	double rainbowNumber;
	public int[] array;
	public int[] placehold;
	public int[] colour;


	public RainbowInsertionSort(Handler handler, int X, int Y, int size, int sleep, int tempRainbowNumber)
	{
		mHandler = handler;
		maxX = X;
		maxY = Y;
		arraySize = size;
		sleepTime = sleep;
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
		mHandler.obtainMessage(0, array).sendToTarget(); // sends the array back to be drawn

		try
		{
			Thread.sleep(100);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		long startTime = System.nanoTime();

		insertionRainbowSort();

		long endTime = System.nanoTime();
		float f = (float) ((endTime - startTime) * 0.000000001);
		mHandler.obtainMessage(4, f).sendToTarget();

		mHandler.obtainMessage(0,array).sendToTarget();
	}


	private void insertionRainbowSort()
	{
		int x;
		int y;
		int j;

		for (int i = 1; i < arraySize; i++)
		{
			x = array[i];
			y = colour[i];
			j = i;

			while (j > 0 && array[j - 1] < x)
			{
				array[j] = array[j - 1];
				colour[j] = colour[j - 1];

				if (sleepTime != 0)
				{
					mHandler.obtainMessage(1, j, Color.BLACK, array).sendToTarget();
					if (j < arraySize - 1) mHandler.obtainMessage(1, j + 1, colour[j + 1], array).sendToTarget();
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

				j--;
			}
			colour[j] = y;
			array[j] = x;

			if (sleepTime != 0)
			{
				if (j < arraySize - 1) mHandler.obtainMessage(1, j + 1, colour[j + 1], array).sendToTarget();
				mHandler.obtainMessage(1, j, colour[j], array).sendToTarget();
				mHandler.obtainMessage(2).sendToTarget();
			}

		}
	}
}