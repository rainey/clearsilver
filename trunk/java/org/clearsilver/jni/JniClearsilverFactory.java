package org.clearsilver.jni;

import org.clearsilver.CS;
import org.clearsilver.ClearsilverFactory;
import org.clearsilver.DelegatedHdf;
import org.clearsilver.HDF;

/**
 * Factory implementation for the original JNI version of Java Clearsilver
 *
 * @author Sergio Marti (smarti@google.com)
 */
public class JniClearsilverFactory implements ClearsilverFactory {

  private final boolean unwrapDelegatedHdfs;

  /**
   * Default constructor. Any {@link org.clearsilver.DelegatedHdf}s passed to
   * {@link #newCs} will be fully unwrapped before being passed to CS
   * implementation constructor.
   */
  public JniClearsilverFactory() {
    this(true);
  }

  /**
   * Constructor that takes the option whether to unwrap all
   * {@link org.clearsilver.DelegatedHdf} objects before passing the
   * {@link org.clearsilver.HDF} object to the {@link org.clearsilver.CS}
   * implementation constructor.
   * <br>
   * Developers that want strict checking that the HDF passed to newCs matches
   * HDF objects constructed by newHDF may want to pass in {@code false}.
   *
   * @param unwrapDelegatedHdfs true if {@link org.clearsilver.HDF}s passed to
   * {@link #newCs} should be unwrapped if they are
   * {@link org.clearsilver.DelegatedHdf} objects, false otherwise.
   */
  public JniClearsilverFactory(boolean unwrapDelegatedHdfs) {
    this.unwrapDelegatedHdfs = unwrapDelegatedHdfs;
  }

  /**
  * Create a new CS object.
  * @param hdf the HDF object to use in constructing the CS object.
  * @return a new CS object
  */
  public CS newCs(HDF hdf) {
    if (unwrapDelegatedHdfs) {
      hdf = DelegatedHdf.getFullyUnwrappedHdf(hdf);
    }
    return new JniCs(JniHdf.cast(hdf));
  }

  /**
   * Create a new CS object.  Also checks and unwraps any DelegatedHdfs
   * passed into the method.
   * @param hdf the HDF object to use in constructing the CS object.
   * @param globalHdf the global HDF object to use in constructing the
   * CS object.
   * @return a new CS object
   */
  public CS newCs(HDF hdf, HDF globalHdf) {
    if (unwrapDelegatedHdfs) {
      hdf = DelegatedHdf.getFullyUnwrappedHdf(hdf);
      globalHdf = DelegatedHdf.getFullyUnwrappedHdf(globalHdf);
    }
    return new JniCs(JniHdf.cast(hdf), JniHdf.cast(globalHdf));
  }

  /**
   * Create a new HDF object.
   * @return a new HDF object
   */
  public HDF newHdf() {
    return new JniHdf();
  }
}
