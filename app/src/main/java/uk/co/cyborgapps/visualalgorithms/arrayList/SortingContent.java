package uk.co.cyborgapps.visualalgorithms.arrayList;

/**
 * Created by sean on 22/04/16 17:00 greenwich mean time.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class SortingContent
{

	/**
	 * An array of sample (arrayList) items.
	 */
	public static List<SortingItem> ITEMS = new ArrayList<SortingItem>();

	/**
	 * A map of sample (arrayList) items, by ID.
	 */
	public static Map<String, SortingItem> ITEM_MAP = new HashMap<String, SortingItem>();

	static
	{
		// Add 3 sample items.
		addItem(new SortingItem("1", "Bubble Sort"));
		addItem(new SortingItem("2", "Rainbow Bubble Sort"));
		addItem(new SortingItem("3", "Quick Sort"));
		addItem(new SortingItem("4", "Rainbow Quick Sort"));
		addItem(new SortingItem("5", "Insertion Sort"));
		addItem(new SortingItem("6", "Rainbow Insertion Sort"));
		addItem(new SortingItem("7", "Selecton Sort"));
		addItem(new SortingItem("8", "Rainbow Selecton Sort"));
		addItem(new SortingItem("9", "Quick sort Multithread"));
		addItem(new SortingItem("10", "Rainbow Quick sort Multithread"));
		addItem(new SortingItem("11", "Quick Sort smart pivot select multit thread"));
	}

	public static String getItem(String id)
	{
		return ITEM_MAP.get(id).content;
	}

	private static void addItem(SortingItem item)
	{
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	/**
	 * A arrayList item representing a piece of content.
	 */
	public static class SortingItem
	{
		public String id;
		public String content;

		public SortingItem(String id, String content)
		{
			this.id = id;
			this.content = content;
		}

		@Override
		public String toString()
		{
			return content;
		}
	}
}
