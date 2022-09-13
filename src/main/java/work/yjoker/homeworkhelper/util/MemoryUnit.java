package work.yjoker.homeworkhelper.util;

/**
 * @author HeYunjia
 */
public enum MemoryUnit {

    /**
     * 位 bit
     */
    BIT {
        public long toBit(long d)      { return d; }
        public long toByte(long d)     { return d/(B/B0); }
        public long toKiloByte(long d) { return d/(K/B0); }
        public long toMegaByte(long d) { return d/(M/B0); }
        public long toGigaByte(long d) { return d/(G/B0); }
        public long convert(long d, MemoryUnit u) { return u.toBit(d); }
    },

    /**
     * 字节 Byte
     */
    BYTE {
        public long toBit(long d)      { return x(d, B/B0, MAX/(B/B0)); }
        public long toByte(long d)     { return d; }
        public long toKiloByte(long d) { return d/(K/B); }
        public long toMegaByte(long d) { return d/(M/B); }
        public long toGigaByte(long d) { return d/(G/B); }
        public long convert(long d, MemoryUnit u) { return u.toByte(d); }
    },

    /**
     * 千字节 KB
     */
    KILOBYTE {
        public long toBit(long d)      { return x(d, K/B0, MAX/(K/B0)); }
        public long toByte(long d)     { return x(d, K/B, MAX/(K/B)); }
        public long toKiloByte(long d) { return d; }
        public long toMegaByte(long d) { return d/(M/K); }
        public long toGigaByte(long d) { return d/(G/K); }
        public long convert(long d, MemoryUnit u) { return u.toKiloByte(d); }
    },

    /**
     * 兆字节 MB
     */
    MEGABYTE {
        public long toBit(long d)      { return x(d, M/B0, MAX/(M/B0)); }
        public long toByte(long d)     { return x(d, M/B, MAX/(M/B)); }
        public long toKiloByte(long d) { return x(d, M/K, MAX/(M/K)); }
        public long toMegaByte(long d) { return d; }
        public long toGigaByte(long d) { return d/(G/M); }
        public long convert(long d, MemoryUnit u) { return u.toMegaByte(d); }
    },

    /**
     * 十亿字节 GB
     */
    GIGABYTE {
        public long toBit(long d)      { return x(d, G/B0, MAX/(G/B0)); }
        public long toByte(long d)     { return x(d, G/B, MAX/(G/B)); }
        public long toKiloByte(long d) { return x(d, G/K, MAX/(G/K)); }
        public long toMegaByte(long d) { return x(d, G/M, MAX/(G/M)); }
        public long toGigaByte(long d) { return d; }
        public long convert(long d, MemoryUnit u) { return u.toGigaByte(d); }
    };

    private static final long B0 = 1L;
    private static final long B = B0 * 1024L;
    private static final long K = B * 1024L;
    private static final long M = K * 1024L;
    private static final long G = M * 1024L;

    private static final long MAX = Long.MAX_VALUE;

    /**
     * return d * m;
     */
    private static long x(long d, long m, long over) {
        if (d >  over) return Long.MAX_VALUE;
        if (d < -over) return Long.MIN_VALUE;
        return d * m;
    }


    /**
     * 转换为 bit
     */
    public long toBit(long d) {
        throw new AbstractMethodError();
    }

    /**
     * 转换为 Byte
     */
    public long toByte(long d) {
        throw new AbstractMethodError();
    }

    /**
     * 转换为 KB
     */
    public long toKiloByte(long d) {
        throw new AbstractMethodError();
    }

    /**
     * 转换为 MB
     */
    public long toMegaByte(long d) {
        throw new AbstractMethodError();
    }

    /**
     * 转换为 GB
     */
    public long toGigaByte(long d) {
        throw new AbstractMethodError();
    }

    /**
     * 将指定单位的转化为本单位
     */
    public long convert(long d, MemoryUnit u) {
        throw new AbstractMethodError();
    }

}
