

## Transcript

Hello everybody!

Today we'll be exploring the geometry of ellipses.
Our goal is to construct a special ellipse.
Given an equilateral triangle, we want to surround it with an ellipse such that it touches the tip of the triangle at a tangent.
How can we define an ellipse that fits perfectly?

That reminds me of a joke.
What did the triangle say to the circle? You're pointless!
Ellipses have no corners so how can we measure them?
I like to think of an ellipse as a squashed circle, but we'll need a more precise notation to make progress.
An ellipse is a curve surrounding two focal points. For every point on the curve, the sum of the distances to both foci is a constant.
We can parameterize an ellipse by 2 constants `a` and `b`.
`a` is the orange outer radius, and `b` is the blue inner radius.
Calculating `y` by `a`, and `x` by `b` results is a nice smooth ellipse.
A circle is an ellipse where `a` equals `b`.
In this case there is only one radius, and a single focal point at the center.
Another way to describe the curve is to sweep an angle using sine and cosine to calculate `x` and `y` coordinates.

The focal point distance from the center is denoted `c`.
`a`, `b` and `c` form edges of a right triangle, which allows us to relate them as Pythagorean triples.
Recall from the definition of an ellipse that the orange cord must be constant length.
Let's consider the case when the cord is stretched vertically.
The foci are symmetrical, so the doubled section below equals the unused section above.
The total length of the cord must be `2a`
Knowing that the cord must be `2a`, we can see that when the cord touches the far right side, a symmetrical triangle is formed, and each leg of the cord must be `a`.
Thus we can form a right triangle relationship between `a`, `b`, and `c`.

Alright, back to our special ellipse.
Time to take a closer look at this triangle.
Let's set the distance from the center to the corners to be 1.
The green diameter must be length 2, and we know that the bottom radius must be exactly divided in half by the base of the triangle by symmetry.
Therefore the height from the base to the top corner must be 1 and a half.
Finding the base width is a little trickier, but again we can use the diameter to form an outer right triangle which must have sides `w`, `1`, and `2`.
Therefore `w` squared must be 2 squared minus 1 squared,
4 minus 1 is 3,
we take the square root and have the side width.
Equilateral triangles fit together to form hexagons that in turn can also fit perfectly together.
Hexagons are a great way to make a space filling structure efficiently.
Now how can we use the dimensions of equilateral triangles to solve our problem?
The diamond is a combination of inner and outer squares, just like the ellipse is a combination of inner and outer radii.
The ellipse needs to perfectly fit the diamond, so the squares must perfectly fit circles of the inner and outer radii `b` and `a`.
We can measure the squares with the triangle.
The pink line must be size of the base of the triangle,
which is the square root of 3.
The green line must be the height of 2 triangles,
which is 3.
And now we can put it all together.
The radius `b` forms a right triangle with the pink line that we know to be the square root of 3.
Thus `b` must be the square root of 3 divided by the square root of 2.
The radius `a` forms a right triangle with the green line that we know to be 3.
Thus `a` must be 3 divided by the square root of 2.

The distance to the foci `c` is the square root of `a` squared minus `b` squared, which simplifies to the square root of 3.
The square root of 3 happens to be the width of the square around the inner radius.
The foci being exactly on the corner of this square only happens for ellipses with this shape.

Eccentricity is another word used to describe how squashed an ellipse is.
Eccentricity is defined as the focal distance `c` divided by the outer radius `a`.
For this ellipse the eccentricity is the square root of 2 times the square root of 3, divided by 3.

The type of squashing we are doing is called affine transformation.
Affine transformation preserves the ratios of sides even though the angles and lengths change.
We can use the ratio of `a` and `b` with the formula for the ellipse and our known point.
We can write `a` in terms of `b`
Substituting `x` and `y` leaves an equation for `b`
Solving for `b` we confirm that `b` is the square root of 3 divided by the square root of 2.
We can use the ratio to determine that `a` must be 3 divided by the square root of 2.
This is the same answer that we calculated before, we just arrived at it from a different perspective.

Let's recap what we've discovered:
An ellipse is defined by 2 radii `a` and `b`
An ellipse is swept by a cord between foci which are both distance `c` from the center
The cord is `2b` long
Affine transformation can be applied to any shape to stretch it

Until next time, keep on diagramming!
