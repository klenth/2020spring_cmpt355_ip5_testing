# Group 0 tests

Files in this directory are ones that every team's parser should be able to parse. (Note that a lot of the code,
especially in Expressions.java, is nonsensical and wouldn't actually compile - but it should still parse!)

As you test your parser on the files, make sure not only that it parses them, but also that the parse tree is correct;
for example, an "else" should be parsed alongside the "if" that it matches, not an earlier "if", and parse trees
should be reflective of operator precedence and associativity.
