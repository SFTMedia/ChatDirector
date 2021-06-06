package com.blalp.chatdirector.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor()
public class Version implements Comparable<Version> {
    @Getter
    int major, minor, patch;

    /**
     * Latest version
     */
    public Version() {
        major = minor = patch = Integer.MAX_VALUE;
    }

    public Version(String version) {
        if (version == null) {
            major = minor = patch = Integer.MAX_VALUE;
            return;
        }
        major = Integer.parseInt(version.split("\\.")[0]);
        minor = Integer.parseInt(version.split("\\.")[1]);
        patch = Integer.parseInt(version.split("\\.")[2]);
    }

    /**
     * 
     * @param other
     * @return +/-3 for a major difference, +/-2 for a minor difference and +/-1 for
     *         a patch difference.
     */
    @Override
    public int compareTo(Version other) {
        if (other.equals(null)) {
            return -4;
        }
        if (major < other.getMajor()) {
            return -3;
        } else if (major == other.getMajor()) {
            if (minor < other.getMinor()) {
                return -2;
            } else if (minor == other.getMinor()) {
                if (patch < other.getPatch()) {
                    return -1;
                } else if (patch == other.getPatch()) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 2;
            }
        } else {
            return 3;
        }
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }
}
