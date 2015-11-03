package org.ometa.imagesearcher.adapters;

/**
 * Created by devin on 11/3/15.
 */
/*
public class InitialBlankArrayAdapter extends ArrayAdapter {

    public InitialBlankArrayAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }

    @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);
            if (position == getCount()) {
                ((TextView)v.findViewById(android.R.id.text1)).setText("");
                // add the hint text to the very end
                ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
            }
            return v;
        }

        @Override
        public int getCount() {
            return super.getCount() - 1; // do not display last item. It is used as hint.
        }
    }
}
*/