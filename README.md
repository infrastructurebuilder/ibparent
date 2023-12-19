# ibparent
JVM parent for InfrastructureBuilder

## Purpose

This parent is meant to be a unifying version and config parent for IB projects.  It configures most of the necessary plugins and manages most of the relevant dependencies for dealing with our internal projects as well as giving us a platform for keeping those up to date.

### Exception Translator

The code from the [com.mscharhag.ET](git@github.com:mscharhag/ET.git) package was directly into `ibexceptions` for modularization.  If the ET project ever releases with modules, we will remove it and start depending on that module.

### GAV Stuff

The Maven GAV equivalents in IB work (GroupId, ArtifactId, Version) stuff was moved into `ibexceptions` because it makes more sense than to be in `ibcore`, as it reduces the necessary footprint of anything in IB needing to have a GAV.  Specifically, this applies to the `ibversions-maven-plugin`, but probably for other stuff, too.

## Changes

This parent releases regularly.  Usually, this means every 2 weeks or so, but might mean more frequently if many things are changing or there is a security concern.  It also might mean that this happens less frequently than that.

### Proposed Changes

If you're using this parent, or any of it's associated other parents, and you're displeased with the dependencies that it manages or how that works, then feel free to suggest a change or submit a PR.  Your suggestion will be given consideration.  However, that does not mean that it will be used.  If that makes your situation untenable with regard to this, or any other IB project, feel free to fork and update.

