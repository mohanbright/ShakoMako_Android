package com.io.app.shakomako.dagger.component

import android.app.Activity
import com.io.app.shakomako.dagger.scope.ActivityScope
import dagger.BindsInstance
import dagger.Subcomponent


@ActivityScope
@Subcomponent
public interface ActivityComponent{
    @Subcomponent.Builder
    interface Builder{
        @BindsInstance
        fun activity(activity: Activity):Builder
        fun build() : ActivityComponent
    }



}



/*@ActivityScope
@Subcomponent
public interface ActivityComponent {

    @Subcomponent.Builder
    interface Builder{
        @BindsInstance
        Builder activity(Activity activity);
        ActivityComponent build();
    }
}*/