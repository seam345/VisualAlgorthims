package uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.bubbleSort;

import android.graphics.Color;
import android.os.Handler;

import java.util.Random;

import uk.co.cyborgapps.visualalgorithms.Constants;

/**
 * Created by sean on 22/04/16 17:00 greenwich mean time.
 */
public class BubbleSortThread extends Thread implements Constants
{
	private int sleepTime;
	private int arraySize;
	//	private final  String TAG = "seanborg";
	private final Handler mHandler;
	private int maxX, maxY;
	private int[] array;

	public BubbleSortThread(Handler handler, int X, int Y, int size, int sleep)
	{
		mHandler = handler;
		maxX = X;
		maxY = Y;
		arraySize = size;
		array = new int[arraySize];
		sleepTime = sleep;
		Random random = new Random();

		for (int i = 0; i < arraySize; i++)        //creates an array filled with random numbers
		{
			array[i] = random.nextInt(maxY);

		}
	}


	@Override
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

		bubbleSort();

		long endTime = System.nanoTime();
		float f = (float) ((endTime - startTime) * 0.000000001);
		mHandler.obtainMessage(4, f).sendToTarget();

		mHandler.obtainMessage(0, array).sendToTarget(); // sends the array back to be drawn

	}

	private void bubbleSort()
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
						mHandler.obtainMessage(1, i, Color.BLUE, array).sendToTarget();
						mHandler.obtainMessage(2).sendToTarget();
					} else
					{
						mHandler.obtainMessage(1, i - 1, Color.BLACK, array).sendToTarget();
						mHandler.obtainMessage(1, i, Color.BLUE, array).sendToTarget();
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

					sorted = false;

					if (sleepTime != 0)
					{
						mHandler.obtainMessage(1, i + 1, Color.BLUE, array).sendToTarget();
						mHandler.obtainMessage(1, i, Color.BLUE, array).sendToTarget();
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
			size = size - 1;


			if (sleepTime != 0)
			{
				mHandler.obtainMessage(1, size, Color.BLACK, array).sendToTarget();
				mHandler.obtainMessage(1, size - 1, Color.BLACK, array).sendToTarget();
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
