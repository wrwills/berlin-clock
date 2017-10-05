# berlin-clock


## Usage

sbt "run 14:28:01"

will produce:

```
_
RR
RRRR
YYRYY
YYY
```

where an "R" represents a lit red light and a "Y" represents a lit yellow light.

sbt "run 00:00"

will produce

```
Y
_
_
_
_
```

Times should be entered in ISO time format.

"sbt test" will run tests.
