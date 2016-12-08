package uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.quickSort;

import android.graphics.Color;
import android.os.Handler;

import java.util.Random;

import uk.co.cyborgapps.visualalgorithms.Constants;

/**
 * Created by sean on 7/8/15.
 */
public class QuickThread extends Thread implements Constants
{
	private int sleepTime;
	private int arraySize;
	//	private final  String TAG = "seanborg";
	private final Handler mHandler;
	private int maxX, maxY;
	private int[] array;

	public QuickThread(Handler handler, int X, int Y, int size, int sleep)
	{
		mHandler = handler;
		maxX = X;
		maxY = Y;
		arraySize = size;
		sleepTime = sleep;
		array = new int[arraySize];
	}

	@Override
	public void run()
	{
		Random random = new Random();

		for (int i = 0; i < arraySize; i++)        //creates an array filled with random numbers
		{
			array[i] = random.nextInt(maxY);

		}

		mHandler.obtainMessage(0, array).sendToTarget(); // sends the array back to be drawn
		try
		{
			Thread.sleep(100);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
			Thread.currentThread().interrupt();
			return;
		}

		long startTime = System.nanoTime();

		quickSort(array, 0, arraySize - 1);

		long endTime = System.nanoTime();
		float f = (float) ((endTime - startTime) * 0.000000001);
		mHandler.obtainMessage(4, f).sendToTarget();

		mHandler.obtainMessage(0, array).sendToTarget(); // sends the array back to be drawn
	}

	private void quickSort(int[] arr, int low, int high)
	{

		if (arr == null || arr.length == 0)
		{
			return;
		}

		if (low >= high)
		{
			return;
		}

		// pick the pivot
		int middle = low + (high - low) / 2;
		int pivot = arr[middle];

		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j)
		{
			while (arr[i] > pivot)
			{
				i++;
				if (sleepTime != 0)
				{
					mHandler.obtainMessage(1, i, Color.BLUE, array).sendToTarget();
					mHandler.obtainMessage(1, i - 1, Color.BLACK, array).sendToTarget();
					mHandler.obtainMessage(2).sendToTarget();
					try
					{
						Thread.sleep(sleepTime);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
						Thread.currentThread().interrupt();
						return;
					}
				}
			}

			while (arr[j] < pivot)
			{
				j--;
				if (sleepTime != 0)
				{
					mHandler.obtainMessage(1, j, Color.BLUE, array).sendToTarget();
					mHandler.obtainMessage(1, j + 1, Color.BLACK, array).sendToTarget();
					mHandler.obtainMessage(2).sendToTarget();
					try
					{
						Thread.sleep(sleepTime);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
						Thread.currentThread().interrupt();
						return;
					}
				}
			}

			if (i <= j)
			{
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;

				if (sleepTime != 0)
				{
					mHandler.obtainMessage(1, i, Color.BLUE, array).sendToTarget();
					mHandler.obtainMessage(1, i - 1, Color.BLACK, array).sendToTarget();
					mHandler.obtainMessage(1, j, Color.BLUE, array).sendToTarget();
					mHandler.obtainMessage(1, j + 1, Color.BLACK, array).sendToTarget();
					mHandler.obtainMessage(2).sendToTarget();
					try
					{
						Thread.sleep(sleepTime);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
						Thread.currentThread().interrupt();
						return;
					}
				}

			}
		}
		if (sleepTime != 0)
		{
			mHandler.obtainMessage(1, i, Color.BLACK, array).sendToTarget();
			mHandler.obtainMessage(1, j, Color.BLACK, array).sendToTarget();
			mHandler.obtainMessage(2).sendToTarget();
		}

		// recursively sort two sub parts
		if (low < j)
		{
			quickSort(arr, low, j);
		}

		if (high > i)
		{
			quickSort(arr, i, high);
		}
	}
}
