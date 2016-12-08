package uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.selectionSort;

import android.graphics.Color;
import android.os.Handler;

import uk.co.cyborgapps.visualalgorithms.Constants;

import java.util.Random;

/**
 * Created by sean on 7/10/15 4:21 PM 4:21 PM ${LOCALE} 4:22 PM pecific coast time.
 */
public class SelectionSortThread extends Thread implements Constants
{
	private int sleepTime;
	private int arraySize;
	//	private final  String TAG = "seanborg";
	private final Handler mHandler;
	private int  maxY;
	private int[] array;

	public SelectionSortThread(Handler handler, int X, int Y, int size, int sleep)
	{
		mHandler = handler;
		maxY = Y;
		sleepTime = sleep;
		arraySize = size;
		array = new int[arraySize];
		Random random = new Random();

		for (int i=0; i<arraySize; i++)		//creates an array filled with random numbers
		{
			array[i] = random.nextInt(maxY);

		}
	}



	public void run()
	{
		mHandler.obtainMessage(0, array).sendToTarget();

		try
		{
			Thread.sleep(sleepTime);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
			return;
		}

		long startTime = System.nanoTime();

		selectionSort();

		long endTime = System.nanoTime();
		float f = (float) ((endTime - startTime) * 0.000000001);
		mHandler.obtainMessage(4, f).sendToTarget();

		mHandler.obtainMessage(0,array).sendToTarget();
	}

	private void selectionSort ()
	{

		int  min;

		for (int i =0; i< arraySize-1; i++)
		{
			min = i;
			for (int j = i; j < arraySize; j++ )
			{

				if (sleepTime !=0)
				{
					mHandler.obtainMessage(1, j, Color.BLUE, array).sendToTarget();
					if ((min != (j - 1)) && j != 0) mHandler.obtainMessage(1, j-1 ,Color.BLACK, array).sendToTarget();
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
					if (sleepTime !=0)
					{
						mHandler.obtainMessage(1, min, Color.BLACK, array).sendToTarget();
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
			}
			if (sleepTime !=0)
			{
				mHandler.obtainMessage(1, min, Color.BLACK, array).sendToTarget();
				mHandler.obtainMessage(1, i, Color.BLACK, array).sendToTarget();
				mHandler.obtainMessage(1, arraySize - 1, Color.BLACK, array).sendToTarget();
				mHandler.obtainMessage(2).sendToTarget();
			}
		}
	}

}
