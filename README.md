# Moonlit
Moonlit is a library that enables you to easily make canvas in Java, inspired by The Coding Train.

## usage
```
import java.awt.Graphics;

import com.mnt1fg.moonlit.Moonlit;
import com.mnt1fg.moonlit.MoonlitInterface;

public class YourClass implements MoonlitInterface {
    //...

    public YourClass() {
        // get instance
        Moonlit moonlit = Moonlit.getInstance();

        // create window with width and height, you must call this first.
        moonlit.createWindow(600, 400);

        // (optional) set how many times onUpdate() is called per seconds.
        // default number is 10
        moonlit.setTicks(20);

        // (optional) register to let onUpdate() method be called.
        moonlit.register(this);

        // show window, which is called finally.
        moonlit.showWindow();
    }

    //...

    @Override
    public void onUpdate(Graphics g) {
        Moonlit moonlit = Moonlit.getInstance();
        //draw something...
    }
}
```

