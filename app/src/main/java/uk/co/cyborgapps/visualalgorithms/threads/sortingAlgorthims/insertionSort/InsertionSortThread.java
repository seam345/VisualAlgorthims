package uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.insertionSort;

import android.graphics.Color;
import android.os.Handler;

import java.util.Random;

import uk.co.cyborgapps.visualalgorithms.Constants;

/**
 * Created by sean on 7/10/15.
 */
public class InsertionSortThread extends Thread implements Constants
{
	private int sleepTime;
	private final int arraySize;
	//	private final  String TAG = "seanborg";
	private final Handler mHandler;
	private int maxX, maxY;
	private int[] array;

	public InsertionSortThread(Handler handler, int X, int Y, int size, int sleep)
	{
		mHandler = handler;
		maxX = X;
		maxY = Y;
		arraySize = size;
		array = new int[arraySize];
		Random random = new Random();
		sleepTime = sleep;

		for (int i = 0; i < arraySize; i++)        //creates an array filled with random numbers
		{
			array[i] = random.nextInt(maxY);

		}
	}

	public void run()
	{
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

		insertionSort();

		long endTime = System.nanoTime();
		float f = (float) ((endTime - startTime) * 0.000000001);
		mHandler.obtainMessage(4, f).sendToTarget();

		mHandler.obtainMessage(0,array).sendToTarget();

	}


	private void insertionSort()
	{
		int x;
		int j;

		for (int i = 1; i < arraySize; i++)
		{
			x = array[i];
			j = i;


			while (j > 0 && array[j - 1] < x)
			{
				array[j] = array[j - 1];

				if (sleepTime != 0)
				{
					mHandler.obtainMessage(1, j, Color.BLUE, array).sendToTarget();
					if (j < arraySize - 1)
						mHandler.obtainMessage(1, j + 1, Color.BLACK, array).sendToTarget();
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
			array[j] = x;
			if (sleepTime != 0)
			{
				if (j < arraySize - 1)
					mHandler.obtainMessage(1, j + 1, Color.BLACK, array).sendToTarget();
				mHandler.obtainMessage(1, j, Color.BLACK, array).sendToTarget();
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
		}
	}
}
