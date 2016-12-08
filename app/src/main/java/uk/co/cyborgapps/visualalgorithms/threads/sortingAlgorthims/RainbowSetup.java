package uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by sean on 22/04/16 17:25 greenwich mean time.
 */
public class RainbowSetup
{


	private int arraySize, maxX, maxY;
	private int[] array, placehold, colour = null;
	private double rainbowNumber;
	private boolean sortArray = true;

	public RainbowSetup(int X, int Y, int size, int tempRainbowNumber)        //todo maybe mess around with the colour make some cool looks and things as becca was bored with the currrent rainbow effect
	{
		maxX = X;
		maxY = Y;
		arraySize = size;
		colour = new int[arraySize];
		placehold = new int[arraySize];
		array = new int[arraySize];
		rainbowNumber = tempRainbowNumber;
	}

	public int[] setupRainbowArray()
	{

		Random random = new Random();

		for (int i = 0; i < arraySize; i++)
		{
			array[i] = random.nextInt(maxY);
			placehold[i] = i;
		}

		quickSort(array, 0, arraySize - 1);

		double frequancy = 6.28318530718 / (arraySize / rainbowNumber);
		double redFreq = 18.8495559215 / (arraySize * 3 / rainbowNumber),
				blueFreq = 12.5663706144 / (arraySize * 2 / rainbowNumber),
				greenFreq = 6.28318530718 / (arraySize / rainbowNumber);
		int red;
		int blue;
		int green;
		int width = 127;
		int center = 128;


		for (int i = 0; i < arraySize; i++)
		{

			red = (int) (Math.sin(redFreq * i + 0 + Math.PI) * width + center);
			green = (int) (Math.sin(greenFreq * i + (2 * Math.PI / 3) + Math.PI) * width + center);
			blue = (int) (Math.sin(blueFreq * i + (4 * Math.PI / 3) + Math.PI) * width + center);

			colour[i] = Color.rgb(red, green, blue);
		}
		sortArray = false;
		quickSort(placehold, 0, arraySize - 1);

		return array;
	}

	public int[] getColour()
	{
		return colour;
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
			}

			while (arr[j] < pivot)
			{
				j--;
			}

			if (i <= j)
			{
				if (sortArray)
				{
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;

					temp = placehold[i];
					placehold[i] = placehold[j];
					placehold[j] = temp;
				} else
				{
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;

					temp = array[i];
					array[i] = array[j];
					array[j] = temp;


					temp = colour[i];
					colour[i] = colour[j];
					colour[j] = temp;
				}

				i++;
				j--;
			}
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

