# bitgit

A bit of git: a very small subset of git, written in Kotlin

Commands implemented: `INIT`, `ADD`, `COMMIT`

You can run it by compiling a jar first*, and then running the following commands:

```
java -jar bitgit.jar init
touch hello
java -jar bitgit.jar add hello
java -jar bitgit.jar commit "my commit message!"
```

java (?) BitGit init
add hello
commit "this is a commit message"

And end up with a directory called `bitgit` (our .git directory) with contents like this:
```
├── HEAD
├── index
├── objects
│   ├── 56
│   │   └── fe2b02990a5b713bf5a765470fd10e04d5470a
│   ├── 87
│   │   └── 6a3bd4b4e5f07832fe52b756577c8011c78f70
│   └── da
│       └── 39a3ee5e6b4b0d3255bfef95601890afd80709
└── refs
    └── heads
        └── master
```

