package uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.quickSort;

import android.graphics.Color;
import android.os.Handler;

import uk.co.cyborgapps.visualalgorithms.Constants;
import uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.RainbowSetup;


/**
 * Created by sean on 6/16/15.
 * performs a quick sort for the rainbow problem
 */
public class RainbowQuickThread extends Thread implements Constants
{
	private int sleepTime;
	private int arraySize;
	//	private static final  String TAG = "seanborg";
	private final Handler mHandler;
	private int[] array;
	private int[] colour;

	public RainbowQuickThread(Handler handler, int maxX, int maxY, int size, int sleep, int rainbowNumber)
	{
		mHandler = handler;
		arraySize = size;
		colour = new int[arraySize];
		array = new int[arraySize];
		sleepTime = sleep;
		RainbowSetup rainbowSetup = new RainbowSetup(maxX, maxY, arraySize, rainbowNumber);
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

	private void quickSort(final int[] arr, final int low, final int high)
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
					mHandler.obtainMessage(1, i, Color.BLACK, array).sendToTarget();
					mHandler.obtainMessage(1, i - 1, colour[i - 1], array).sendToTarget();
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
					mHandler.obtainMessage(1, j, Color.BLACK, array).sendToTarget();
					mHandler.obtainMessage(1, j + 1, colour[j + 1], array).sendToTarget();
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

				temp = colour[i];
				colour[i] = colour[j];
				colour[j] = temp;
				i++;
				j--;

				if (sleepTime != 0)
				{
					mHandler.obtainMessage(1, i, Color.BLACK, array).sendToTarget();
					mHandler.obtainMessage(1, i - 1, colour[i - 1], array).sendToTarget();
					mHandler.obtainMessage(1, j, Color.BLACK, array).sendToTarget();
					mHandler.obtainMessage(1, j + 1, colour[j + 1], array).sendToTarget();
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
			mHandler.obtainMessage(1, i, colour[i], array).sendToTarget();
			mHandler.obtainMessage(1, j, colour[j], array).sendToTarget();
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


