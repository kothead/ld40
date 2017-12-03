package com.kothead.ld40.model.component;

import com.badlogic.ashley.core.Component;
import com.kothead.ld40.model.Direction;

public class ControlComponent implements Component {

    public Direction direction;
    public boolean jump;
    public boolean divide;
    public boolean die;
}
