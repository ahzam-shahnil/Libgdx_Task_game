package com.badlogic.drop.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.drop.Main;

public class HtmlLauncher extends GwtApplication {
   @Override
   public GwtApplicationConfiguration getConfig () {
      return new GwtApplicationConfiguration(800, 480);
   }

   @Override
   public ApplicationListener createApplicationListener () {
      return new Main();
   }
}
