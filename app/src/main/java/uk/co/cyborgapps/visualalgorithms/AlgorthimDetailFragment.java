package uk.co.cyborgapps.visualalgorithms;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.cyborgapps.visualalgorithms.arrayList.SortingContent;
import uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.bubbleSort.BubbleSortThread;
import uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.bubbleSort.RainbowBubbleThread;
import uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.insertionSort.InsertionSortThread;
import uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.insertionSort.RainbowInsertionSort;
import uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.quickSort.QuickSortMultiThread;
import uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.quickSort.QuickSortSmartPivotMultiThread;
import uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.quickSort.QuickThread;
import uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.quickSort.RainbowQuickSortMultiThread;
import uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.quickSort.RainbowQuickThread;
import uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.selectionSort.RainbowSelectionSort;
import uk.co.cyborgapps.visualalgorithms.threads.sortingAlgorthims.selectionSort.SelectionSortThread;

/**
 * A fragment representing a single Algorthim detail screen.
 * This fragment is either contained in a {@link AlgorthimListActivity}
 * in two-pane mode (on tablets) or a {@link AlgorthimDetailActivity}
 * on handsets.
 */
/**
 * A fragment representing a single Algorthim detail screen.
 * This fragment is either contained in a {@link AlgorthimListActivity}
 * in two-pane mode (on tablets) or a {@link AlgorthimDetailActivity}
 * on handsets.
 */
public class AlgorthimDetailFragment extends Fragment
{

	Thread thread = null;
	Button startSortButton;
	EditText arraySizeEditText, noOFRainbowsEditText, speedEditText;
	TextView timeTakenTextView;
	ImageView graphView;
	Boolean redraw = true , interuptThread = false;
	Canvas canvas;
	int arraySize, maxX, maxY, rainbowNumber, sleepTime;

	int[]  colour = null;

//	private static final  String TAG = "seanborg";


	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The arrayList content this fragment is presenting.
	 */
	private SortingContent.SortingItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public AlgorthimDetailFragment()
	{
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID))
		{
			// Load the arrayList content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = SortingContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_algorthim_detail, container, false);

		startSortButton = (Button) rootView.findViewById(R.id.start_sort_button);
		startSortButton.setOnClickListener(startSort);

		speedEditText = (EditText) rootView.findViewById(R.id.speed_text);
		speedEditText.setOnFocusChangeListener(focusChangeListener);
		timeTakenTextView = (TextView) rootView.findViewById(R.id.time_text);
		timeTakenTextView.setOnFocusChangeListener(focusChangeListener);

		graphView= ((ImageView) rootView.findViewById(R.id.graph_view));
		graphView.setOnClickListener(clearFocus);
		noOFRainbowsEditText = ((EditText) rootView.findViewById(R.id.rainbow_repeat_number));
		arraySizeEditText = ((EditText) rootView.findViewById(R.id.array_size));

		switch (Integer.parseInt(mItem.id)%2)
		{
			case 0:
				noOFRainbowsEditText.setHint("Rainbows max = 0");
				arraySizeEditText.addTextChangedListener(rainbowNumberTextWatcher);
				break;
			case 1:
				noOFRainbowsEditText.setVisibility(View.GONE);
				break;
		}

		ViewTreeObserver vto =  graphView.getViewTreeObserver();		//todo work out exactly what this is and how it allows me to  get the image view size on load
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
								 {
									 public boolean onPreDraw()
									 {
										 graphView.getViewTreeObserver().removeOnPreDrawListener(this);
										 maxX = graphView.getMeasuredWidth();
										 maxY = graphView.getMeasuredHeight();
										 arraySizeEditText.setHint("Max bar No. = " + (Integer.toString(maxX)));
										 return true;
									 }
								 }
		);
		return rootView;
	}


	TextWatcher rainbowNumberTextWatcher = new TextWatcher()
	{
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{

			if (count != 0)
			{
				int x = (Integer.parseInt(String.valueOf(s)));
				x = x / 7;
				noOFRainbowsEditText.setHint("Rainbows max = " + String.valueOf(x));
			}else
			{
				noOFRainbowsEditText.setHint("Rainbows max = 0");
			}

		}

		@Override
		public void afterTextChanged(Editable s)
		{

		}
	};


	View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener()
	{
		@Override
		public void onFocusChange(View view, boolean b)
		{
			switch (view.getId())
			{
				case R.id.speed_text:
					if (b)
					{
						timeTakenTextView.setVisibility(View.GONE);
					}else
					{
						timeTakenTextView.setVisibility(View.VISIBLE);
					}
					break;
				case R.id.time_text:

					if (b)
					{
						speedEditText.setVisibility(View.GONE);
					}else
					{
						speedEditText.setVisibility(View.VISIBLE);
					}
					break;
				case R.id.graph_view:
					speedEditText.clearFocus();
					timeTakenTextView.clearFocus();
					break;

			}
		}
	};


	View.OnClickListener clearFocus = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			speedEditText.clearFocus();
			timeTakenTextView.clearFocus();
		}
	};




	View.OnClickListener startSort = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			speedEditText.clearFocus();
			timeTakenTextView.clearFocus();
			if (interuptThread) thread.interrupt();
			try
			{
				arraySize = Integer.parseInt(arraySizeEditText.getText().toString());
			} catch (Exception e)
			{
				arraySize = 50;
			}
			try
			{
				rainbowNumber = Integer.parseInt(noOFRainbowsEditText.getText().toString());
			} catch (Exception e)
			{
				rainbowNumber = 1;
			}
			try
			{
				sleepTime = Integer.parseInt(speedEditText.getText().toString());
			}catch ( Exception e)
			{
				sleepTime= 30;
			}
			interuptThread = true;
			switch (Integer.parseInt(mItem.id))
			{
				case 1:
					thread = new BubbleSortThread(mHandler.get(), graphView.getMeasuredWidth(), graphView.getMeasuredHeight(), arraySize, sleepTime);
					thread.start();
					break;
				case 2:

					thread = new RainbowBubbleThread(mHandler.get(), graphView.getMeasuredWidth(), graphView.getMeasuredHeight(), arraySize, sleepTime, rainbowNumber);
					thread.start();
					break;
				case 3:
					thread = new QuickThread(mHandler.get(), graphView.getMeasuredWidth(), graphView.getMeasuredHeight(), arraySize, sleepTime);
					thread.start();
					break;
				case 4:
					thread = new RainbowQuickThread(mHandler.get(), graphView.getMeasuredWidth(), graphView.getMeasuredHeight(), arraySize, sleepTime, rainbowNumber);
					thread.start();
					break;
				case 5:
					thread = new InsertionSortThread(mHandler.get(), graphView.getMeasuredWidth(), graphView.getMeasuredHeight(), arraySize, sleepTime);
					thread.start();
					break;
				case 6:
					thread = new RainbowInsertionSort(mHandler.get(), graphView.getMeasuredWidth(), graphView.getMeasuredHeight(), arraySize, sleepTime, rainbowNumber);
					thread.start();
					break;
				case 7:
					thread = new SelectionSortThread(mHandler.get(), graphView.getMeasuredWidth(), graphView.getMeasuredHeight(), arraySize, sleepTime);
					thread.start();
					break;
				case 8:
					thread = new RainbowSelectionSort(mHandler.get(), graphView.getMeasuredWidth(), graphView.getMeasuredHeight(), arraySize, sleepTime, rainbowNumber);
					thread.start();
					break;
				case 9:
					thread = new QuickSortMultiThread(mHandler.get(), graphView.getMeasuredWidth(), graphView.getMeasuredHeight(), arraySize, sleepTime);
					thread.start();
					break;
				case 10:
					thread = new RainbowQuickSortMultiThread(mHandler.get(), graphView.getMeasuredWidth(), graphView.getMeasuredHeight(), arraySize, sleepTime, rainbowNumber);
					thread.start();
					break;
				case 11:
					thread = new QuickSortSmartPivotMultiThread(mHandler.get(), graphView.getMeasuredWidth(), graphView.getMeasuredHeight(), arraySize,sleepTime);
					thread.start();
//					break;
			}
		}
	};

	@Override
	public void onStart()
	{
		super.onStart();
		redraw =true;
	}

	private final ThreadLocal<Handler> mHandler = new ThreadLocal<Handler>()
	{
		@Override
		protected Handler initialValue()
		{
			return new Handler()    //this funky thing talkes to the other code pages called threads it does so without interrupting the user input thread allowing me to run code in the background.
					//This is used to talk to the threads on user input. and for the threads to relay back to the Main (display, user input) thread such as when they have connected or have rescieved a message
			{
				@Override
				public void handleMessage(Message msg)
				{
					switch (msg.what)
					{
						case 0:
							drawGraph((int[]) msg.obj, colour,-1,0);	//case  will draw a whole new graph
							break;
						case 1:
							if (redraw)		//todo think about how to save the lines that may be erased due to redraw may not be important as it redraws the next lines so soon
							{
								drawGraph((int[]) msg.obj, colour, -1,0);
								redraw = false;
							} else
							{
								drawGraph((int[]) msg.obj, colour, msg.arg1, msg.arg2);
							}
							break;
						case 2:
							graphView.invalidate();
							break;
						case 3:
							colour = (int[]) msg.obj;
							break;
						case 4:
							String text = msg.obj + " s";
							timeTakenTextView.setText(text);
							break;
					}
				}
			};
		}
	};

	public void initiateCanvas ()
	{
		Bitmap bitmap;
		bitmap= Bitmap.createBitmap(maxX, maxY, Bitmap.Config.ARGB_8888);
		canvas = new Canvas(bitmap);

		graphView.setImageBitmap(bitmap);
	}

	public void drawGraph ( int[] array,int[] colourArray, int positionOne, int oneColour)
	{
		Paint paint;
		final int maxX = graphView.getMeasuredWidth();
		final int maxY = graphView.getMeasuredHeight();

		if (redraw)
		{
			initiateCanvas();
		}

		paint = new Paint();
		int x =0;
		if (positionOne >= 0)	// to save computation time redrawing the whole graph again we shall just draw over the lines changed in the background colour (white) and then redraw them in the correct size and colour.
		{
			for (int t = 0; t < (maxX / arraySize); t++)
			{

				paint.setColor(Color.WHITE);
				canvas.drawLine((((maxX / arraySize) * positionOne) + t), maxY, (((maxX / arraySize) * positionOne) + t), 0, paint);    //delete bar 1

				paint.setColor(oneColour);
				canvas.drawLine((((maxX / arraySize) * positionOne) + t), maxY, (((maxX / arraySize) * positionOne) + t), array[positionOne], paint);
			}
		}else
		{
			canvas.drawColor(Color.WHITE);
			for (int i = 0; i < arraySize; i++)
			{
				if (colourArray == null)
				{
					paint.setColor(Color.BLACK);
				}else
				{
					paint.setColor(colourArray[i]);
				}

				for (int t = 0; t < (maxX / arraySize); t++)
				{
					canvas.drawLine(x + t, maxY, x + t, array[i], paint);
				}
				x = (maxX / arraySize) + x;
			}
			graphView.invalidate();
		}


	}
}