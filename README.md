# JavaQuickDraw

An [old] library to decode QuickDraw images (aka Macintosh Pict files) in Java.

The original code was written for Java 1.0, I refactored once the code during my PhD to use Java 1.1 facilities, and I am currently in the process of cleaning up the code. My goal would be to write a newer rendering back-end based on Graphics2 facilities. The current code uses hacks to go around the limitations of the original graphics class.

Currently, the code supports the following opcodes (both Quickdraw 1 and 2 format):

* Lines, Rectangles, Rounded Rectangles, Polygons, Circles.
* The draw and fill operations are fully supported, there are hacks for the highlight and invert operations, but they don’t work well.
* Color operations. The library supports color selection using both the very old quickdraw 1 color operators and the quickdraw 2 operations. Patterns are currently converted into colors.
* Basic text-rendering with very limited font selection (basically times and helvetica). The library associates the text and the rectangles, so you can ask the library for the text at a given point.
* Pixmaps, the library supports 1,2,4,8,16 and 24 bit bitmaps both in uncompressed and RLE compressed format.
Embedded Quicktime Media. The library supports images encoded using the photo/jpeg codec.
* There is preliminary code for region operations. It does not work yet. But even Apple seems to have trouble with those, Mac OS X’s preview seems unable to handle them correctly.
* Polygon start and end comments are used to speed up polygon rendering by avoiding the recalculations of colors between lines.
* Text begin and end comments are used to build the strings associated with rectangles. This avoids that a word or sentence broken up in multiple drawText operations is understood as being multiple text bits.

Here are the things I would like to do, time permitting.

* Implement a more modern GraphPort implementation that uses the Graphics2 operations.
* Render patterns as patterns.
* Render the invert mode correctly.
* Implement a more modern Applet with double buffering and some basic swing interface.
* Improve the text handling, in particular, extra-spacing in text.
* More robust QuickTime opcode handling.
* Handle more codecs, TIFF, BMP and such seem doable.

For more information, see [the page describing the project on my blog](http://wiesmann.codiferes.net/wordpress/?page_id=267).


