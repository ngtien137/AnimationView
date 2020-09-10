# AnimationView
## Features
- Support configure animation from anim resources from xml and code
- Bind to lifecycle (auto pause and resume depend on lifecycle)
## Getting Started
### Configure build.gradle (Project)
* Add these lines:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
### Configure build gradle (Module):
* Import module base:
```gradle
dependencies {
  implementation 'com.github.ngtien137:AnimationView:Tag'
}
```
* You can get version of this module [here](https://jitpack.io/#ngtien137/AnimationView)
### Using
* Use custom views: AnimationTextView, AnimationView, AnimationImageView with custom attributes:
* Example:
```xml
<com.lhd.animation_view.AnimationImageView
    android:layout_width="100dp"
    android:layout_height="100dp"
    app:animation_resource="@anim/anim_rotate"
    android:src="@android:drawable/ic_menu_add"
    app:animation_bind_to_life_cycle="true"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent" />

<com.lhd.animation_view.AnimationTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Hello World!"
    android:textColor="#00f502"
    app:animation_bind_to_life_cycle="true"
    app:animation_resource="@anim/anim_tap_to_text"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
```
