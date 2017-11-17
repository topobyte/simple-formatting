# simple-formatting

This library is a minimalistic and lightweight replacement for the good old
`String.format()` method from the JDK. It is motivated by the fact that
frameworks such as GWT and JSweet do not port this method (because it is too
complicated and has deep dependencies into private classes of the JDK).

Our replacement does not strive to implement all fancy features of
`String.format()`, instead it concentrates on those features, that we tend
to use a lot (and which are few). However, it may over time grow into a more
or less fully featured replacement.

See the
[TODO](https://github.com/topobyte/simple-formatting/blob/master/TODO.md)
list if you want to see what's missing from our point of view. Feel free to
submit pull requests for items mentioned there and other missing features.
