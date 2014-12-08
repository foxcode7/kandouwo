package com.kindleren.kandouwo.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kindleren.kandouwo.R;

/**
 * Created by xuezhangbin on 14/12/1.
 */
public class BaseListFragment extends BaseFragment{
    static final int INTERNAL_PROGRESS_CONTAINER_ID = 0x00ff0002;
    static final int INTERNAL_LIST_CONTAINER_ID = 0x00ff0003;
    static final int INTERNAL_DEFAULT_EMPTY_ID = 0x00ff0004;
    static final int INTERNAL_ERROR_EMPTY_ID = 0x00ff0005;
    final private Handler mHandler = new Handler();
    final private Runnable mRequestFocus = new Runnable() {
        public void run() {
            mList.focusableViewAvailable(mList);
        }
    };
    final private AdapterView.OnItemClickListener mOnClickListener
            = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            final int headerViewsCount = getListView().getHeaderViewsCount();
            int realPosition = position - headerViewsCount;
            if (getListAdapter() != null && realPosition < getListAdapter().getCount()) {
                onListItemClick((ListView) parent, v, position - headerViewsCount, id);
            }
        }
    };
    final private AdapterView.OnItemLongClickListener mOnItemLongClickListenr = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            final int headerViewsCount = getListView().getHeaderViewsCount();
            int realPosition = position - headerViewsCount;
            if (getListAdapter() != null && realPosition < getListAdapter().getCount()) {
                return onListItemLongClick((ListView) adapterView, view, position - headerViewsCount, id);
            }
            return false;
        }

    };
    ListAdapter mAdapter;
    ListView mList;
    View mEmptyView;
    View mProgressContainer;
    View mListContainer;
    CharSequence mEmptyText;
    boolean mListShown;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context context = getActivity();

        FrameLayout root = new FrameLayout(context);

        LinearLayout pframe = createProgressContainer(context);
        pframe.setId(INTERNAL_PROGRESS_CONTAINER_ID);
        root.addView(pframe, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        FrameLayout lframe = new FrameLayout(context);
        lframe.setId(INTERNAL_LIST_CONTAINER_ID);

        View ev = createEmptyView();
        lframe.addView(ev, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        View lv = createListView();
        lframe.addView(lv, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        root.addView(lframe, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        root.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return root;
    }

    /**
     * Attach to list view once the view hierarchy has been created.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ensureList();
    }

    /**
     * Detach from list view.
     */
    @Override
    public void onDestroyView() {
        mHandler.removeCallbacks(mRequestFocus);
        mList = null;
        mListShown = false;
        mEmptyView = mProgressContainer = mListContainer = null;
        super.onDestroyView();
    }

    /**
     * This method will be called when an item in the list is selected.
     * Subclasses should override. Subclasses can call
     * getListView().getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param l        The ListView where the click happened
     * @param v        The view that was clicked within the ListView
     * @param position The position of the view in the list
     * @param id       The row id of the item that was clicked
     */
    public void onListItemClick(ListView l, View v, int position, long id) {
    }

    public boolean onListItemLongClick(ListView l, View view, int position, long id) {
        return false;
    }

    /**
     * Set the currently selected list item to the specified
     * position with the adapter's data
     *
     * @param position
     */
    public void setSelection(int position) {
        ensureList();
        mList.setSelection(position);
    }

    /**
     * Get the position of the currently selected list item.
     */
    public int getSelectedItemPosition() {
        ensureList();
        return mList.getSelectedItemPosition();
    }

    /**
     * Get the cursor row ID of the currently selected list item.
     */
    public long getSelectedItemId() {
        ensureList();
        return mList.getSelectedItemId();
    }

    /**
     * Get the activity's list view widget.
     */
    public ListView getListView() {
        ensureList();
        return mList;
    }

    /**
     * Control whether the list is being displayed.  You can make it not
     * displayed if you are waiting for the initial data to show in it.  During
     * this time an indeterminant progress indicator will be shown instead.
     * <p/>
     * <p>Applications do not normally need to use this themselves.  The default
     * behavior of ListFragment is to start with the list not being shown, only
     * showing it once an adapter is given with {@link #setListAdapter(android.widget.ListAdapter)}.
     * If the list at that point had not been shown, when it does get shown
     * it will be do without the user ever seeing the hidden state.
     *
     * @param shown If true, the list view is shown; if false, the progress
     *              indicator.  The initial value is true.
     */
    public void setListShown(boolean shown) {
        setListShown(shown, true);
    }

    /**
     * Like {@link #setListShown(boolean)}, but no animation is used when
     * transitioning from the previous state.
     */
    public void setListShownNoAnimation(boolean shown) {
        setListShown(shown, false);
    }

    /**
     * Control whether the list is being displayed.  You can make it not
     * displayed if you are waiting for the initial data to show in it.  During
     * this time an indeterminant progress indicator will be shown instead.
     *
     * @param shown   If true, the list view is shown; if false, the progress
     *                indicator.  The initial value is true.
     * @param animate If true, an animation will be used to transition to the
     *                new state.
     */
    private void setListShown(boolean shown, boolean animate) {
        ensureList();
        if (mProgressContainer == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        if (mListShown == shown) {
            return;
        }
        mListShown = shown;
        if (shown) {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_out));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_in));
            } else {
                mProgressContainer.clearAnimation();
                mListContainer.clearAnimation();
            }
            mProgressContainer.setVisibility(View.GONE);
            mListContainer.setVisibility(View.VISIBLE);
        } else {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_in));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_out));
            } else {
                mProgressContainer.clearAnimation();
                mListContainer.clearAnimation();
            }
            mProgressContainer.setVisibility(View.VISIBLE);
            mListContainer.setVisibility(View.GONE);
        }
    }

    /**
     * Get the ListAdapter associated with this activity's ListView.
     */
    public ListAdapter getListAdapter() {
        return mAdapter;
    }

    /**
     * Provide the cursor for the list view.
     */
    public void setListAdapter(ListAdapter adapter) {
        boolean hadAdapter = mAdapter != null;
        mAdapter = adapter;
        if (mList != null) {
            mList.setAdapter(adapter);
            if (!mListShown && !hadAdapter) {
                // The list was hidden, and previously didn't have an
                // adapter.  It is now time to show it.
                setListShown(true, getView().getWindowToken() != null);
            }
        }
    }

    protected void ensureList() {
        if (mList != null) {
            return;
        }
        View root = getView();
        if (root == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        if (root instanceof ListView) {
            mList = (ListView) root;
        } else {
            mEmptyView = root.findViewById(android.R.id.empty);
            mProgressContainer = root.findViewById(INTERNAL_PROGRESS_CONTAINER_ID);
            mListContainer = root.findViewById(INTERNAL_LIST_CONTAINER_ID);
            View rawListView = root.findViewById(android.R.id.list);
            if (!(rawListView instanceof ListView)) {
                if (rawListView == null) {
                    throw new RuntimeException(
                            "Your content must have a ListView whose id attribute is " +
                                    "'android.R.id.list'");
                }
                throw new RuntimeException(
                        "Content has view with id attribute 'android.R.id.list' "
                                + "that is not a ListView class");
            }
            mList = (ListView) rawListView;
            if (mEmptyView != null) {
                mList.setEmptyView(mEmptyView);
            }
        }
        mListShown = true;
        mList.setOnItemClickListener(mOnClickListener);
        mList.setOnItemLongClickListener(mOnItemLongClickListenr);
        if (mAdapter != null) {
            ListAdapter adapter = mAdapter;
            mAdapter = null;
            setListAdapter(adapter);
        } else {
            // We are starting without an adapter, so assume we won't
            // have our data right away and start with the progress indicator.
            if (mProgressContainer != null) {
                setListShown(false, false);
            }
        }
        mHandler.post(mRequestFocus);
    }

    private View createEmptyView() {
        FrameLayout eFrame = new FrameLayout(getActivity());
        eFrame.setId(android.R.id.empty);
        View defaultEmptyView = createDefaultEmptyView();
        defaultEmptyView.setId(INTERNAL_DEFAULT_EMPTY_ID);
        eFrame.addView(defaultEmptyView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        View errorEmptyView = createErrorEmptyView();
        errorEmptyView.setId(INTERNAL_ERROR_EMPTY_ID);
        errorEmptyView.setVisibility(View.GONE);
        eFrame.addView(errorEmptyView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        return eFrame;
    }

    protected View createDefaultEmptyView() {
        TextView view = new TextView(getActivity());
        view.setText(getEmptyText());
        return view;
    }

    protected View createErrorEmptyView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.error, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setListShown(false);
                refresh();
            }
        });
        return view;
    }

    protected CharSequence getEmptyText() {
        return getString(R.string.empty_info);
    }

    public void setEmptyState(boolean isError) {
        ensureList();
        getDefaultEmptyView().setVisibility(isError ? View.GONE : View.VISIBLE);
        getErrorEmptyView().setVisibility(isError ? View.VISIBLE : View.GONE);
    }

    public View getErrorEmptyView() {
        return mEmptyView.findViewById(INTERNAL_ERROR_EMPTY_ID);
    }

    public View getDefaultEmptyView() {
        return mEmptyView.findViewById(INTERNAL_DEFAULT_EMPTY_ID);
    }

    protected void refresh() {
    }

    /**
     * must contain a ListView with id = android.R.id.list
     *
     * @return
     */
    protected View createListView() {
        ListView lv = new ListView(getActivity());
        lv.setId(android.R.id.list);
        lv.setDrawSelectorOnTop(true);
        return lv;
    }

    protected LinearLayout createProgressContainer(Context context) {
        LinearLayout pframe = new LinearLayout(context);
        pframe.setOrientation(LinearLayout.VERTICAL);
        pframe.setVisibility(View.GONE);
        pframe.setGravity(Gravity.CENTER);

        View progressBar = LayoutInflater.from(context).inflate(R.layout.progress_layout, null);
        pframe.addView(progressBar, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        return pframe;
    }
}
