# VisualizationSorting
One more approach for animating Quick Sort Algorithm on Android

In this application I used trend approaches to implement custom visualization for Quick Sort.

A main activity provides UI for:
- inserting array of digits;
- specifying frequency of animation;
- and control-buttons.

Based on this, user can type list of positive digits separated by whitespaces, and then click on "Start" button to start sorting algorithm.

During the algorithm user would see updates on the screen:
- every digits represented as vertical bar;
- height of the bar equals scaled value of a digit;
- maximum digits(bars) move to the right of screen.

User cann interrupt algorithm via click on "Stop" button.

# Technical Fetures
As I sad the applucation based on trend approaches, at least, I think so ))

Firstly, most of code are written by Kotlin. Secondly, the application uses for DI Dagger2 and ComponentBuilders. For background processing I used RxJava.

# Todo
Next time I will try use OpenGL instead.
Also I going to add another sorting algorithms and visualizating formats.
