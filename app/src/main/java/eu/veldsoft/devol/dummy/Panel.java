package eu.veldsoft.devol.dummy;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * It is a dummy class. It was created only to bypass compilation error.
 */
public class Panel extends Component {
    protected void addNotify() {
    }

    protected boolean action(Event event, Object argument) {
        return false;
    }

    protected void paint(Canvas graphics) {
    }

    protected Rect bounds() {
        return null;
    }

    protected void reshape(int x, int y, int width, int height) {
    }

    protected void resize(int width, int height) {
    }

    protected void setBackground(int color) {
    }

    protected void setForeground(int color) {
    }

    protected void setLayout(FlowLayout layout) {
    }

    public void setLayout(GridBagLayout layout) {
    }
}
