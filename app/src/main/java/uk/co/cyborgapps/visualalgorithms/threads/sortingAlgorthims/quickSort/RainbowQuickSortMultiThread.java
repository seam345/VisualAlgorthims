package uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.quickSort;

import android.graphics.Color;
import android.os.Handler;

import uk.co.cyborgapps.visualalgorithms.Constants;
import uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.RainbowSetup;

/**
 * Created by sean on 29/07/15 11:37 pecific coast time.
 */
public class RainbowQuickSortMultiThread extends Thread implements Constants
{
	private int sleepTime;
	private int arraySize;
	//	private static final  String TAG = "seanborg";
	private final Handler mHandler;
	private int maxX, maxY;
	private int[] array;
	private int[] colour;
	private double rainbowNumber;

	public RainbowQuickSortMultiThread(Handler handler, int X, int Y, int size,int sleep, int tempRainbowNumber)
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
			return;

		if (low >= high)
			return;

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
					mHandler.obtainMessage(1, i-1, colour[i-1], array).sendToTarget();
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
					mHandler.obtainMessage(1, j+1, colour[j+1], array).sendToTarget();
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
				colour[j]=temp;
				i++;
				j--;

				if (sleepTime != 0)
				{
					mHandler.obtainMessage(1, i, Color.BLACK, array).sendToTarget();
					mHandler.obtainMessage(1, i-1, colour[i-1], array).sendToTarget();
					mHandler.obtainMessage(1, j, Color.BLACK, array).sendToTarget();
					mHandler.obtainMessage(1, j+1, colour[j+1], array).sendToTarget();
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
			if (j > -1) mHandler.obtainMessage(1, j, colour[j], array).sendToTarget(); //todo why it goes to minus 1 idk
			mHandler.obtainMessage(2).sendToTarget();
		}

		// recursively sort two sub parts
//		if (j > -1)
//		{
			final int finalJ = j;
			Runnable runnable = new Runnable()
			{
				@Override
				public void run()
				{
					if (low < finalJ)
						quickSort(arr, low, finalJ);
				}
			};

			Thread thread = new Thread(runnable);
			thread.start();
//		}



		final int finalI = i;
		Runnable runnable1 = new Runnable()
		{
			@Override
			public void run()
			{
				if (high > finalI)
					quickSort(arr, finalI, high);
			}
		};

		Thread thread1 = new Thread(runnable1);
		thread1.start();

		try
		{
			thread1.join();
			thread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
			Thread.currentThread().interrupt();
			return;
		}

	}
}
