'use strict';

//
// Set of functions added to standard String Type. Due to the fact that there
// may already be defined in some browsers, we have first to test if every
// functions that we define exists by this check :
//    if (typeof String.prototype.FunctionWeWantToDefine !== 'function')
//

/**
 * The endsWith method determines whether a string ends with the
 * characters of another string, returning true or false as appropriate.
 *
 * @param suffix {string} The characters to be searched for at the end of this string.
 * @returns {boolean}
 */
if (typeof String.prototype.endsWith !== 'function') {
  String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
  };
}
