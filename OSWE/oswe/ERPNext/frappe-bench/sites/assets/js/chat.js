(function () {
	'use strict';

	var commonjsGlobal = typeof window !== 'undefined' ? window : typeof global !== 'undefined' ? global : typeof self !== 'undefined' ? self : {};

	function unwrapExports (x) {
		return x && x.__esModule && Object.prototype.hasOwnProperty.call(x, 'default') ? x['default'] : x;
	}

	function createCommonjsModule(fn, module) {
		return module = { exports: {} }, fn(module, module.exports), module.exports;
	}

	var fuse = createCommonjsModule(function (module, exports) {
	/*!
	 * Fuse.js v3.4.2 - Lightweight fuzzy-search (http://fusejs.io)
	 * 
	 * Copyright (c) 2012-2017 Kirollos Risk (http://kiro.me)
	 * All Rights Reserved. Apache Software License 2.0
	 * 
	 * http://www.apache.org/licenses/LICENSE-2.0
	 */
	(function webpackUniversalModuleDefinition(root, factory) {
		{ module.exports = factory(); }
	})(commonjsGlobal, function() {
	return /******/ (function(modules) { // webpackBootstrap
	/******/ 	// The module cache
	/******/ 	var installedModules = {};
	/******/
	/******/ 	// The require function
	/******/ 	function __webpack_require__(moduleId) {
	/******/
	/******/ 		// Check if module is in cache
	/******/ 		if(installedModules[moduleId]) {
	/******/ 			return installedModules[moduleId].exports;
	/******/ 		}
	/******/ 		// Create a new module (and put it into the cache)
	/******/ 		var module = installedModules[moduleId] = {
	/******/ 			i: moduleId,
	/******/ 			l: false,
	/******/ 			exports: {}
	/******/ 		};
	/******/
	/******/ 		// Execute the module function
	/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
	/******/
	/******/ 		// Flag the module as loaded
	/******/ 		module.l = true;
	/******/
	/******/ 		// Return the exports of the module
	/******/ 		return module.exports;
	/******/ 	}
	/******/
	/******/
	/******/ 	// expose the modules object (__webpack_modules__)
	/******/ 	__webpack_require__.m = modules;
	/******/
	/******/ 	// expose the module cache
	/******/ 	__webpack_require__.c = installedModules;
	/******/
	/******/ 	// define getter function for harmony exports
	/******/ 	__webpack_require__.d = function(exports, name, getter) {
	/******/ 		if(!__webpack_require__.o(exports, name)) {
	/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
	/******/ 		}
	/******/ 	};
	/******/
	/******/ 	// define __esModule on exports
	/******/ 	__webpack_require__.r = function(exports) {
	/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
	/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
	/******/ 		}
	/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
	/******/ 	};
	/******/
	/******/ 	// create a fake namespace object
	/******/ 	// mode & 1: value is a module id, require it
	/******/ 	// mode & 2: merge all properties of value into the ns
	/******/ 	// mode & 4: return value when already ns object
	/******/ 	// mode & 8|1: behave like require
	/******/ 	__webpack_require__.t = function(value, mode) {
	/******/ 		if(mode & 1) { value = __webpack_require__(value); }
	/******/ 		if(mode & 8) { return value; }
	/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) { return value; }
	/******/ 		var ns = Object.create(null);
	/******/ 		__webpack_require__.r(ns);
	/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
	/******/ 		if(mode & 2 && typeof value != 'string') { for(var key in value) { __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key)); } }
	/******/ 		return ns;
	/******/ 	};
	/******/
	/******/ 	// getDefaultExport function for compatibility with non-harmony modules
	/******/ 	__webpack_require__.n = function(module) {
	/******/ 		var getter = module && module.__esModule ?
	/******/ 			function getDefault() { return module['default']; } :
	/******/ 			function getModuleExports() { return module; };
	/******/ 		__webpack_require__.d(getter, 'a', getter);
	/******/ 		return getter;
	/******/ 	};
	/******/
	/******/ 	// Object.prototype.hasOwnProperty.call
	/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
	/******/
	/******/ 	// __webpack_public_path__
	/******/ 	__webpack_require__.p = "";
	/******/
	/******/
	/******/ 	// Load entry module and return exports
	/******/ 	return __webpack_require__(__webpack_require__.s = "./src/index.js");
	/******/ })
	/************************************************************************/
	/******/ ({

	/***/ "./src/bitap/bitap_matched_indices.js":
	/*!********************************************!*\
	  !*** ./src/bitap/bitap_matched_indices.js ***!
	  \********************************************/
	/*! no static exports found */
	/***/ (function(module, exports) {

	module.exports = function () {
	  var matchmask = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : [];
	  var minMatchCharLength = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : 1;
	  var matchedIndices = [];
	  var start = -1;
	  var end = -1;
	  var i = 0;

	  for (var len = matchmask.length; i < len; i += 1) {
	    var match = matchmask[i];

	    if (match && start === -1) {
	      start = i;
	    } else if (!match && start !== -1) {
	      end = i - 1;

	      if (end - start + 1 >= minMatchCharLength) {
	        matchedIndices.push([start, end]);
	      }

	      start = -1;
	    }
	  } // (i-1 - start) + 1 => i - start


	  if (matchmask[i - 1] && i - start >= minMatchCharLength) {
	    matchedIndices.push([start, i - 1]);
	  }

	  return matchedIndices;
	};

	/***/ }),

	/***/ "./src/bitap/bitap_pattern_alphabet.js":
	/*!*********************************************!*\
	  !*** ./src/bitap/bitap_pattern_alphabet.js ***!
	  \*********************************************/
	/*! no static exports found */
	/***/ (function(module, exports) {

	module.exports = function (pattern) {
	  var mask = {};
	  var len = pattern.length;

	  for (var i = 0; i < len; i += 1) {
	    mask[pattern.charAt(i)] = 0;
	  }

	  for (var _i = 0; _i < len; _i += 1) {
	    mask[pattern.charAt(_i)] |= 1 << len - _i - 1;
	  }

	  return mask;
	};

	/***/ }),

	/***/ "./src/bitap/bitap_regex_search.js":
	/*!*****************************************!*\
	  !*** ./src/bitap/bitap_regex_search.js ***!
	  \*****************************************/
	/*! no static exports found */
	/***/ (function(module, exports) {

	var SPECIAL_CHARS_REGEX = /[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g;

	module.exports = function (text, pattern) {
	  var tokenSeparator = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : / +/g;
	  var regex = new RegExp(pattern.replace(SPECIAL_CHARS_REGEX, '\\$&').replace(tokenSeparator, '|'));
	  var matches = text.match(regex);
	  var isMatch = !!matches;
	  var matchedIndices = [];

	  if (isMatch) {
	    for (var i = 0, matchesLen = matches.length; i < matchesLen; i += 1) {
	      var match = matches[i];
	      matchedIndices.push([text.indexOf(match), match.length - 1]);
	    }
	  }

	  return {
	    // TODO: revisit this score
	    score: isMatch ? 0.5 : 1,
	    isMatch: isMatch,
	    matchedIndices: matchedIndices
	  };
	};

	/***/ }),

	/***/ "./src/bitap/bitap_score.js":
	/*!**********************************!*\
	  !*** ./src/bitap/bitap_score.js ***!
	  \**********************************/
	/*! no static exports found */
	/***/ (function(module, exports) {

	module.exports = function (pattern, _ref) {
	  var _ref$errors = _ref.errors,
	      errors = _ref$errors === void 0 ? 0 : _ref$errors,
	      _ref$currentLocation = _ref.currentLocation,
	      currentLocation = _ref$currentLocation === void 0 ? 0 : _ref$currentLocation,
	      _ref$expectedLocation = _ref.expectedLocation,
	      expectedLocation = _ref$expectedLocation === void 0 ? 0 : _ref$expectedLocation,
	      _ref$distance = _ref.distance,
	      distance = _ref$distance === void 0 ? 100 : _ref$distance;
	  var accuracy = errors / pattern.length;
	  var proximity = Math.abs(expectedLocation - currentLocation);

	  if (!distance) {
	    // Dodge divide by zero error.
	    return proximity ? 1.0 : accuracy;
	  }

	  return accuracy + proximity / distance;
	};

	/***/ }),

	/***/ "./src/bitap/bitap_search.js":
	/*!***********************************!*\
	  !*** ./src/bitap/bitap_search.js ***!
	  \***********************************/
	/*! no static exports found */
	/***/ (function(module, exports, __webpack_require__) {

	var bitapScore = __webpack_require__(/*! ./bitap_score */ "./src/bitap/bitap_score.js");

	var matchedIndices = __webpack_require__(/*! ./bitap_matched_indices */ "./src/bitap/bitap_matched_indices.js");

	module.exports = function (text, pattern, patternAlphabet, _ref) {
	  var _ref$location = _ref.location,
	      location = _ref$location === void 0 ? 0 : _ref$location,
	      _ref$distance = _ref.distance,
	      distance = _ref$distance === void 0 ? 100 : _ref$distance,
	      _ref$threshold = _ref.threshold,
	      threshold = _ref$threshold === void 0 ? 0.6 : _ref$threshold,
	      _ref$findAllMatches = _ref.findAllMatches,
	      findAllMatches = _ref$findAllMatches === void 0 ? false : _ref$findAllMatches,
	      _ref$minMatchCharLeng = _ref.minMatchCharLength,
	      minMatchCharLength = _ref$minMatchCharLeng === void 0 ? 1 : _ref$minMatchCharLeng;
	  var expectedLocation = location; // Set starting location at beginning text and initialize the alphabet.

	  var textLen = text.length; // Highest score beyond which we give up.

	  var currentThreshold = threshold; // Is there a nearby exact match? (speedup)

	  var bestLocation = text.indexOf(pattern, expectedLocation);
	  var patternLen = pattern.length; // a mask of the matches

	  var matchMask = [];

	  for (var i = 0; i < textLen; i += 1) {
	    matchMask[i] = 0;
	  }

	  if (bestLocation !== -1) {
	    var score = bitapScore(pattern, {
	      errors: 0,
	      currentLocation: bestLocation,
	      expectedLocation: expectedLocation,
	      distance: distance
	    });
	    currentThreshold = Math.min(score, currentThreshold); // What about in the other direction? (speed up)

	    bestLocation = text.lastIndexOf(pattern, expectedLocation + patternLen);

	    if (bestLocation !== -1) {
	      var _score = bitapScore(pattern, {
	        errors: 0,
	        currentLocation: bestLocation,
	        expectedLocation: expectedLocation,
	        distance: distance
	      });

	      currentThreshold = Math.min(_score, currentThreshold);
	    }
	  } // Reset the best location


	  bestLocation = -1;
	  var lastBitArr = [];
	  var finalScore = 1;
	  var binMax = patternLen + textLen;
	  var mask = 1 << patternLen - 1;

	  for (var _i = 0; _i < patternLen; _i += 1) {
	    // Scan for the best match; each iteration allows for one more error.
	    // Run a binary search to determine how far from the match location we can stray
	    // at this error level.
	    var binMin = 0;
	    var binMid = binMax;

	    while (binMin < binMid) {
	      var _score3 = bitapScore(pattern, {
	        errors: _i,
	        currentLocation: expectedLocation + binMid,
	        expectedLocation: expectedLocation,
	        distance: distance
	      });

	      if (_score3 <= currentThreshold) {
	        binMin = binMid;
	      } else {
	        binMax = binMid;
	      }

	      binMid = Math.floor((binMax - binMin) / 2 + binMin);
	    } // Use the result from this iteration as the maximum for the next.


	    binMax = binMid;
	    var start = Math.max(1, expectedLocation - binMid + 1);
	    var finish = findAllMatches ? textLen : Math.min(expectedLocation + binMid, textLen) + patternLen; // Initialize the bit array

	    var bitArr = Array(finish + 2);
	    bitArr[finish + 1] = (1 << _i) - 1;

	    for (var j = finish; j >= start; j -= 1) {
	      var currentLocation = j - 1;
	      var charMatch = patternAlphabet[text.charAt(currentLocation)];

	      if (charMatch) {
	        matchMask[currentLocation] = 1;
	      } // First pass: exact match


	      bitArr[j] = (bitArr[j + 1] << 1 | 1) & charMatch; // Subsequent passes: fuzzy match

	      if (_i !== 0) {
	        bitArr[j] |= (lastBitArr[j + 1] | lastBitArr[j]) << 1 | 1 | lastBitArr[j + 1];
	      }

	      if (bitArr[j] & mask) {
	        finalScore = bitapScore(pattern, {
	          errors: _i,
	          currentLocation: currentLocation,
	          expectedLocation: expectedLocation,
	          distance: distance
	        }); // This match will almost certainly be better than any existing match.
	        // But check anyway.

	        if (finalScore <= currentThreshold) {
	          // Indeed it is
	          currentThreshold = finalScore;
	          bestLocation = currentLocation; // Already passed `loc`, downhill from here on in.

	          if (bestLocation <= expectedLocation) {
	            break;
	          } // When passing `bestLocation`, don't exceed our current distance from `expectedLocation`.


	          start = Math.max(1, 2 * expectedLocation - bestLocation);
	        }
	      }
	    } // No hope for a (better) match at greater error levels.


	    var _score2 = bitapScore(pattern, {
	      errors: _i + 1,
	      currentLocation: expectedLocation,
	      expectedLocation: expectedLocation,
	      distance: distance
	    }); // console.log('score', score, finalScore)


	    if (_score2 > currentThreshold) {
	      break;
	    }

	    lastBitArr = bitArr;
	  } // console.log('FINAL SCORE', finalScore)
	  // Count exact matches (those with a score of 0) to be "almost" exact


	  return {
	    isMatch: bestLocation >= 0,
	    score: finalScore === 0 ? 0.001 : finalScore,
	    matchedIndices: matchedIndices(matchMask, minMatchCharLength)
	  };
	};

	/***/ }),

	/***/ "./src/bitap/index.js":
	/*!****************************!*\
	  !*** ./src/bitap/index.js ***!
	  \****************************/
	/*! no static exports found */
	/***/ (function(module, exports, __webpack_require__) {

	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

	function _defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) { descriptor.writable = true; } Object.defineProperty(target, descriptor.key, descriptor); } }

	function _createClass(Constructor, protoProps, staticProps) { if (protoProps) { _defineProperties(Constructor.prototype, protoProps); } if (staticProps) { _defineProperties(Constructor, staticProps); } return Constructor; }

	var bitapRegexSearch = __webpack_require__(/*! ./bitap_regex_search */ "./src/bitap/bitap_regex_search.js");

	var bitapSearch = __webpack_require__(/*! ./bitap_search */ "./src/bitap/bitap_search.js");

	var patternAlphabet = __webpack_require__(/*! ./bitap_pattern_alphabet */ "./src/bitap/bitap_pattern_alphabet.js");

	var Bitap =
	/*#__PURE__*/
	function () {
	  function Bitap(pattern, _ref) {
	    var _ref$location = _ref.location,
	        location = _ref$location === void 0 ? 0 : _ref$location,
	        _ref$distance = _ref.distance,
	        distance = _ref$distance === void 0 ? 100 : _ref$distance,
	        _ref$threshold = _ref.threshold,
	        threshold = _ref$threshold === void 0 ? 0.6 : _ref$threshold,
	        _ref$maxPatternLength = _ref.maxPatternLength,
	        maxPatternLength = _ref$maxPatternLength === void 0 ? 32 : _ref$maxPatternLength,
	        _ref$isCaseSensitive = _ref.isCaseSensitive,
	        isCaseSensitive = _ref$isCaseSensitive === void 0 ? false : _ref$isCaseSensitive,
	        _ref$tokenSeparator = _ref.tokenSeparator,
	        tokenSeparator = _ref$tokenSeparator === void 0 ? / +/g : _ref$tokenSeparator,
	        _ref$findAllMatches = _ref.findAllMatches,
	        findAllMatches = _ref$findAllMatches === void 0 ? false : _ref$findAllMatches,
	        _ref$minMatchCharLeng = _ref.minMatchCharLength,
	        minMatchCharLength = _ref$minMatchCharLeng === void 0 ? 1 : _ref$minMatchCharLeng;

	    _classCallCheck(this, Bitap);

	    this.options = {
	      location: location,
	      distance: distance,
	      threshold: threshold,
	      maxPatternLength: maxPatternLength,
	      isCaseSensitive: isCaseSensitive,
	      tokenSeparator: tokenSeparator,
	      findAllMatches: findAllMatches,
	      minMatchCharLength: minMatchCharLength
	    };
	    this.pattern = this.options.isCaseSensitive ? pattern : pattern.toLowerCase();

	    if (this.pattern.length <= maxPatternLength) {
	      this.patternAlphabet = patternAlphabet(this.pattern);
	    }
	  }

	  _createClass(Bitap, [{
	    key: "search",
	    value: function search(text) {
	      if (!this.options.isCaseSensitive) {
	        text = text.toLowerCase();
	      } // Exact match


	      if (this.pattern === text) {
	        return {
	          isMatch: true,
	          score: 0,
	          matchedIndices: [[0, text.length - 1]]
	        };
	      } // When pattern length is greater than the machine word length, just do a a regex comparison


	      var _this$options = this.options,
	          maxPatternLength = _this$options.maxPatternLength,
	          tokenSeparator = _this$options.tokenSeparator;

	      if (this.pattern.length > maxPatternLength) {
	        return bitapRegexSearch(text, this.pattern, tokenSeparator);
	      } // Otherwise, use Bitap algorithm


	      var _this$options2 = this.options,
	          location = _this$options2.location,
	          distance = _this$options2.distance,
	          threshold = _this$options2.threshold,
	          findAllMatches = _this$options2.findAllMatches,
	          minMatchCharLength = _this$options2.minMatchCharLength;
	      return bitapSearch(text, this.pattern, this.patternAlphabet, {
	        location: location,
	        distance: distance,
	        threshold: threshold,
	        findAllMatches: findAllMatches,
	        minMatchCharLength: minMatchCharLength
	      });
	    }
	  }]);

	  return Bitap;
	}(); // let x = new Bitap("od mn war", {})
	// let result = x.search("Old Man's War")
	// console.log(result)


	module.exports = Bitap;

	/***/ }),

	/***/ "./src/helpers/deep_value.js":
	/*!***********************************!*\
	  !*** ./src/helpers/deep_value.js ***!
	  \***********************************/
	/*! no static exports found */
	/***/ (function(module, exports, __webpack_require__) {

	var isArray = __webpack_require__(/*! ./is_array */ "./src/helpers/is_array.js");

	var deepValue = function deepValue(obj, path, list) {
	  if (!path) {
	    // If there's no path left, we've gotten to the object we care about.
	    list.push(obj);
	  } else {
	    var dotIndex = path.indexOf('.');
	    var firstSegment = path;
	    var remaining = null;

	    if (dotIndex !== -1) {
	      firstSegment = path.slice(0, dotIndex);
	      remaining = path.slice(dotIndex + 1);
	    }

	    var value = obj[firstSegment];

	    if (value !== null && value !== undefined) {
	      if (!remaining && (typeof value === 'string' || typeof value === 'number')) {
	        list.push(value.toString());
	      } else if (isArray(value)) {
	        // Search each item in the array.
	        for (var i = 0, len = value.length; i < len; i += 1) {
	          deepValue(value[i], remaining, list);
	        }
	      } else if (remaining) {
	        // An object. Recurse further.
	        deepValue(value, remaining, list);
	      }
	    }
	  }

	  return list;
	};

	module.exports = function (obj, path) {
	  return deepValue(obj, path, []);
	};

	/***/ }),

	/***/ "./src/helpers/is_array.js":
	/*!*********************************!*\
	  !*** ./src/helpers/is_array.js ***!
	  \*********************************/
	/*! no static exports found */
	/***/ (function(module, exports) {

	module.exports = function (obj) {
	  return !Array.isArray ? Object.prototype.toString.call(obj) === '[object Array]' : Array.isArray(obj);
	};

	/***/ }),

	/***/ "./src/index.js":
	/*!**********************!*\
	  !*** ./src/index.js ***!
	  \**********************/
	/*! no static exports found */
	/***/ (function(module, exports, __webpack_require__) {

	function _typeof(obj) { if (typeof Symbol === "function" && typeof Symbol.iterator === "symbol") { _typeof = function _typeof(obj) { return typeof obj; }; } else { _typeof = function _typeof(obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }; } return _typeof(obj); }

	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

	function _defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) { descriptor.writable = true; } Object.defineProperty(target, descriptor.key, descriptor); } }

	function _createClass(Constructor, protoProps, staticProps) { if (protoProps) { _defineProperties(Constructor.prototype, protoProps); } if (staticProps) { _defineProperties(Constructor, staticProps); } return Constructor; }

	var Bitap = __webpack_require__(/*! ./bitap */ "./src/bitap/index.js");

	var deepValue = __webpack_require__(/*! ./helpers/deep_value */ "./src/helpers/deep_value.js");

	var isArray = __webpack_require__(/*! ./helpers/is_array */ "./src/helpers/is_array.js");

	var Fuse =
	/*#__PURE__*/
	function () {
	  function Fuse(list, _ref) {
	    var _ref$location = _ref.location,
	        location = _ref$location === void 0 ? 0 : _ref$location,
	        _ref$distance = _ref.distance,
	        distance = _ref$distance === void 0 ? 100 : _ref$distance,
	        _ref$threshold = _ref.threshold,
	        threshold = _ref$threshold === void 0 ? 0.6 : _ref$threshold,
	        _ref$maxPatternLength = _ref.maxPatternLength,
	        maxPatternLength = _ref$maxPatternLength === void 0 ? 32 : _ref$maxPatternLength,
	        _ref$caseSensitive = _ref.caseSensitive,
	        caseSensitive = _ref$caseSensitive === void 0 ? false : _ref$caseSensitive,
	        _ref$tokenSeparator = _ref.tokenSeparator,
	        tokenSeparator = _ref$tokenSeparator === void 0 ? / +/g : _ref$tokenSeparator,
	        _ref$findAllMatches = _ref.findAllMatches,
	        findAllMatches = _ref$findAllMatches === void 0 ? false : _ref$findAllMatches,
	        _ref$minMatchCharLeng = _ref.minMatchCharLength,
	        minMatchCharLength = _ref$minMatchCharLeng === void 0 ? 1 : _ref$minMatchCharLeng,
	        _ref$id = _ref.id,
	        id = _ref$id === void 0 ? null : _ref$id,
	        _ref$keys = _ref.keys,
	        keys = _ref$keys === void 0 ? [] : _ref$keys,
	        _ref$shouldSort = _ref.shouldSort,
	        shouldSort = _ref$shouldSort === void 0 ? true : _ref$shouldSort,
	        _ref$getFn = _ref.getFn,
	        getFn = _ref$getFn === void 0 ? deepValue : _ref$getFn,
	        _ref$sortFn = _ref.sortFn,
	        sortFn = _ref$sortFn === void 0 ? function (a, b) {
	      return a.score - b.score;
	    } : _ref$sortFn,
	        _ref$tokenize = _ref.tokenize,
	        tokenize = _ref$tokenize === void 0 ? false : _ref$tokenize,
	        _ref$matchAllTokens = _ref.matchAllTokens,
	        matchAllTokens = _ref$matchAllTokens === void 0 ? false : _ref$matchAllTokens,
	        _ref$includeMatches = _ref.includeMatches,
	        includeMatches = _ref$includeMatches === void 0 ? false : _ref$includeMatches,
	        _ref$includeScore = _ref.includeScore,
	        includeScore = _ref$includeScore === void 0 ? false : _ref$includeScore,
	        _ref$verbose = _ref.verbose,
	        verbose = _ref$verbose === void 0 ? false : _ref$verbose;

	    _classCallCheck(this, Fuse);

	    this.options = {
	      location: location,
	      distance: distance,
	      threshold: threshold,
	      maxPatternLength: maxPatternLength,
	      isCaseSensitive: caseSensitive,
	      tokenSeparator: tokenSeparator,
	      findAllMatches: findAllMatches,
	      minMatchCharLength: minMatchCharLength,
	      id: id,
	      keys: keys,
	      includeMatches: includeMatches,
	      includeScore: includeScore,
	      shouldSort: shouldSort,
	      getFn: getFn,
	      sortFn: sortFn,
	      verbose: verbose,
	      tokenize: tokenize,
	      matchAllTokens: matchAllTokens
	    };
	    this.setCollection(list);
	  }

	  _createClass(Fuse, [{
	    key: "setCollection",
	    value: function setCollection(list) {
	      this.list = list;
	      return list;
	    }
	  }, {
	    key: "search",
	    value: function search(pattern) {
	      var opts = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {
	        limit: false
	      };

	      this._log("---------\nSearch pattern: \"".concat(pattern, "\""));

	      var _this$_prepareSearche = this._prepareSearchers(pattern),
	          tokenSearchers = _this$_prepareSearche.tokenSearchers,
	          fullSearcher = _this$_prepareSearche.fullSearcher;

	      var _this$_search = this._search(tokenSearchers, fullSearcher),
	          weights = _this$_search.weights,
	          results = _this$_search.results;

	      this._computeScore(weights, results);

	      if (this.options.shouldSort) {
	        this._sort(results);
	      }

	      if (opts.limit && typeof opts.limit === 'number') {
	        results = results.slice(0, opts.limit);
	      }

	      return this._format(results);
	    }
	  }, {
	    key: "_prepareSearchers",
	    value: function _prepareSearchers() {
	      var pattern = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : '';
	      var tokenSearchers = [];

	      if (this.options.tokenize) {
	        // Tokenize on the separator
	        var tokens = pattern.split(this.options.tokenSeparator);

	        for (var i = 0, len = tokens.length; i < len; i += 1) {
	          tokenSearchers.push(new Bitap(tokens[i], this.options));
	        }
	      }

	      var fullSearcher = new Bitap(pattern, this.options);
	      return {
	        tokenSearchers: tokenSearchers,
	        fullSearcher: fullSearcher
	      };
	    }
	  }, {
	    key: "_search",
	    value: function _search() {
	      var tokenSearchers = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : [];
	      var fullSearcher = arguments.length > 1 ? arguments[1] : undefined;
	      var list = this.list;
	      var resultMap = {};
	      var results = []; // Check the first item in the list, if it's a string, then we assume
	      // that every item in the list is also a string, and thus it's a flattened array.

	      if (typeof list[0] === 'string') {
	        // Iterate over every item
	        for (var i = 0, len = list.length; i < len; i += 1) {
	          this._analyze({
	            key: '',
	            value: list[i],
	            record: i,
	            index: i
	          }, {
	            resultMap: resultMap,
	            results: results,
	            tokenSearchers: tokenSearchers,
	            fullSearcher: fullSearcher
	          });
	        }

	        return {
	          weights: null,
	          results: results
	        };
	      } // Otherwise, the first item is an Object (hopefully), and thus the searching
	      // is done on the values of the keys of each item.


	      var weights = {};

	      for (var _i = 0, _len = list.length; _i < _len; _i += 1) {
	        var item = list[_i]; // Iterate over every key

	        for (var j = 0, keysLen = this.options.keys.length; j < keysLen; j += 1) {
	          var key = this.options.keys[j];

	          if (typeof key !== 'string') {
	            weights[key.name] = {
	              weight: 1 - key.weight || 1
	            };

	            if (key.weight <= 0 || key.weight > 1) {
	              throw new Error('Key weight has to be > 0 and <= 1');
	            }

	            key = key.name;
	          } else {
	            weights[key] = {
	              weight: 1
	            };
	          }

	          this._analyze({
	            key: key,
	            value: this.options.getFn(item, key),
	            record: item,
	            index: _i
	          }, {
	            resultMap: resultMap,
	            results: results,
	            tokenSearchers: tokenSearchers,
	            fullSearcher: fullSearcher
	          });
	        }
	      }

	      return {
	        weights: weights,
	        results: results
	      };
	    }
	  }, {
	    key: "_analyze",
	    value: function _analyze(_ref2, _ref3) {
	      var key = _ref2.key,
	          _ref2$arrayIndex = _ref2.arrayIndex,
	          arrayIndex = _ref2$arrayIndex === void 0 ? -1 : _ref2$arrayIndex,
	          value = _ref2.value,
	          record = _ref2.record,
	          index = _ref2.index;
	      var _ref3$tokenSearchers = _ref3.tokenSearchers,
	          tokenSearchers = _ref3$tokenSearchers === void 0 ? [] : _ref3$tokenSearchers,
	          _ref3$fullSearcher = _ref3.fullSearcher,
	          fullSearcher = _ref3$fullSearcher === void 0 ? [] : _ref3$fullSearcher,
	          _ref3$resultMap = _ref3.resultMap,
	          resultMap = _ref3$resultMap === void 0 ? {} : _ref3$resultMap,
	          _ref3$results = _ref3.results,
	          results = _ref3$results === void 0 ? [] : _ref3$results;

	      // Check if the texvaluet can be searched
	      if (value === undefined || value === null) {
	        return;
	      }

	      var exists = false;
	      var averageScore = -1;
	      var numTextMatches = 0;

	      if (typeof value === 'string') {
	        this._log("\nKey: ".concat(key === '' ? '-' : key));

	        var mainSearchResult = fullSearcher.search(value);

	        this._log("Full text: \"".concat(value, "\", score: ").concat(mainSearchResult.score));

	        if (this.options.tokenize) {
	          var words = value.split(this.options.tokenSeparator);
	          var scores = [];

	          for (var i = 0; i < tokenSearchers.length; i += 1) {
	            var tokenSearcher = tokenSearchers[i];

	            this._log("\nPattern: \"".concat(tokenSearcher.pattern, "\"")); // let tokenScores = []


	            var hasMatchInText = false;

	            for (var j = 0; j < words.length; j += 1) {
	              var word = words[j];
	              var tokenSearchResult = tokenSearcher.search(word);
	              var obj = {};

	              if (tokenSearchResult.isMatch) {
	                obj[word] = tokenSearchResult.score;
	                exists = true;
	                hasMatchInText = true;
	                scores.push(tokenSearchResult.score);
	              } else {
	                obj[word] = 1;

	                if (!this.options.matchAllTokens) {
	                  scores.push(1);
	                }
	              }

	              this._log("Token: \"".concat(word, "\", score: ").concat(obj[word])); // tokenScores.push(obj)

	            }

	            if (hasMatchInText) {
	              numTextMatches += 1;
	            }
	          }

	          averageScore = scores[0];
	          var scoresLen = scores.length;

	          for (var _i2 = 1; _i2 < scoresLen; _i2 += 1) {
	            averageScore += scores[_i2];
	          }

	          averageScore = averageScore / scoresLen;

	          this._log('Token score average:', averageScore);
	        }

	        var finalScore = mainSearchResult.score;

	        if (averageScore > -1) {
	          finalScore = (finalScore + averageScore) / 2;
	        }

	        this._log('Score average:', finalScore);

	        var checkTextMatches = this.options.tokenize && this.options.matchAllTokens ? numTextMatches >= tokenSearchers.length : true;

	        this._log("\nCheck Matches: ".concat(checkTextMatches)); // If a match is found, add the item to <rawResults>, including its score


	        if ((exists || mainSearchResult.isMatch) && checkTextMatches) {
	          // Check if the item already exists in our results
	          var existingResult = resultMap[index];

	          if (existingResult) {
	            // Use the lowest score
	            // existingResult.score, bitapResult.score
	            existingResult.output.push({
	              key: key,
	              arrayIndex: arrayIndex,
	              value: value,
	              score: finalScore,
	              matchedIndices: mainSearchResult.matchedIndices
	            });
	          } else {
	            // Add it to the raw result list
	            resultMap[index] = {
	              item: record,
	              output: [{
	                key: key,
	                arrayIndex: arrayIndex,
	                value: value,
	                score: finalScore,
	                matchedIndices: mainSearchResult.matchedIndices
	              }]
	            };
	            results.push(resultMap[index]);
	          }
	        }
	      } else if (isArray(value)) {
	        for (var _i3 = 0, len = value.length; _i3 < len; _i3 += 1) {
	          this._analyze({
	            key: key,
	            arrayIndex: _i3,
	            value: value[_i3],
	            record: record,
	            index: index
	          }, {
	            resultMap: resultMap,
	            results: results,
	            tokenSearchers: tokenSearchers,
	            fullSearcher: fullSearcher
	          });
	        }
	      }
	    }
	  }, {
	    key: "_computeScore",
	    value: function _computeScore(weights, results) {
	      this._log('\n\nComputing score:\n');

	      for (var i = 0, len = results.length; i < len; i += 1) {
	        var output = results[i].output;
	        var scoreLen = output.length;
	        var currScore = 1;
	        var bestScore = 1;

	        for (var j = 0; j < scoreLen; j += 1) {
	          var weight = weights ? weights[output[j].key].weight : 1;
	          var score = weight === 1 ? output[j].score : output[j].score || 0.001;
	          var nScore = score * weight;

	          if (weight !== 1) {
	            bestScore = Math.min(bestScore, nScore);
	          } else {
	            output[j].nScore = nScore;
	            currScore *= nScore;
	          }
	        }

	        results[i].score = bestScore === 1 ? currScore : bestScore;

	        this._log(results[i]);
	      }
	    }
	  }, {
	    key: "_sort",
	    value: function _sort(results) {
	      this._log('\n\nSorting....');

	      results.sort(this.options.sortFn);
	    }
	  }, {
	    key: "_format",
	    value: function _format(results) {
	      var finalOutput = [];

	      if (this.options.verbose) {
	        var cache = [];

	        this._log('\n\nOutput:\n\n', JSON.stringify(results, function (key, value) {
	          if (_typeof(value) === 'object' && value !== null) {
	            if (cache.indexOf(value) !== -1) {
	              // Circular reference found, discard key
	              return;
	            } // Store value in our collection


	            cache.push(value);
	          }

	          return value;
	        }));

	        cache = null;
	      }

	      var transformers = [];

	      if (this.options.includeMatches) {
	        transformers.push(function (result, data) {
	          var output = result.output;
	          data.matches = [];

	          for (var i = 0, len = output.length; i < len; i += 1) {
	            var item = output[i];

	            if (item.matchedIndices.length === 0) {
	              continue;
	            }

	            var obj = {
	              indices: item.matchedIndices,
	              value: item.value
	            };

	            if (item.key) {
	              obj.key = item.key;
	            }

	            if (item.hasOwnProperty('arrayIndex') && item.arrayIndex > -1) {
	              obj.arrayIndex = item.arrayIndex;
	            }

	            data.matches.push(obj);
	          }
	        });
	      }

	      if (this.options.includeScore) {
	        transformers.push(function (result, data) {
	          data.score = result.score;
	        });
	      }

	      for (var i = 0, len = results.length; i < len; i += 1) {
	        var result = results[i];

	        if (this.options.id) {
	          result.item = this.options.getFn(result.item, this.options.id)[0];
	        }

	        if (!transformers.length) {
	          finalOutput.push(result.item);
	          continue;
	        }

	        var data = {
	          item: result.item
	        };

	        for (var j = 0, _len2 = transformers.length; j < _len2; j += 1) {
	          transformers[j](result, data);
	        }

	        finalOutput.push(data);
	      }

	      return finalOutput;
	    }
	  }, {
	    key: "_log",
	    value: function _log() {
	      if (this.options.verbose) {
	        var _console;

	        (_console = console).log.apply(_console, arguments);
	      }
	    }
	  }]);

	  return Fuse;
	}();

	module.exports = Fuse;

	/***/ })

	/******/ });
	});
	});

	var Fuse = unwrapExports(fuse);

	var hyper_min = createCommonjsModule(function (module) {
	!function(){function e(){}function t(t,n){
	var arguments$1 = arguments;
	var o,r,i,l,a=W;for(l=arguments.length;l-- >2;){ S.push(arguments$1[l]); }n&&null!=n.children&&(S.length||S.push(n.children),delete n.children);while(S.length){ if((r=S.pop())&&void 0!==r.pop){ for(l=r.length;l--;){ S.push(r[l]); } }else{ "boolean"==typeof r&&(r=null),(i="function"!=typeof t)&&(null==r?r="":"number"==typeof r?r+="":"string"!=typeof r&&(i=!1)),i&&o?a[a.length-1]+=r:a===W?a=[r]:a.push(r),o=i; } }var _=new e;return _.nodeName=t,_.children=a,_.attributes=null==n?void 0:n,_.key=null==n?void 0:n.key,_}function n(e,t){for(var n in t){ e[n]=t[n]; }return e}function o(e){!e.__d&&(e.__d=!0)&&1==A.push(e)&&(V)(r);}function r(){var e,t=A;A=[];while(e=t.pop()){ e.__d&&N(e); }}function i(e,t,n){return "string"==typeof t||"number"==typeof t?void 0!==e.splitText:"string"==typeof t.nodeName?!e._componentConstructor&&l(e,t.nodeName):n||e._componentConstructor===t.nodeName}function l(e,t){return e.__n===t||e.nodeName.toLowerCase()===t.toLowerCase()}function a(e){var t=n({},e.attributes);t.children=e.children;var o=e.nodeName.defaultProps;if(void 0!==o){ for(var r in o){ void 0===t[r]&&(t[r]=o[r]); } }return t}function _(e,t){var n=t?document.createElementNS("http://www.w3.org/2000/svg",e):document.createElement(e);return n.__n=e,n}function u(e){var t=e.parentNode;t&&t.removeChild(e);}function p(e,t,n,o,r){if("className"===t&&(t="class"),"key"===t);else if("ref"===t){ n&&n(null),o&&o(e); }else if("class"!==t||r){ if("style"===t){if(o&&"string"!=typeof o&&"string"!=typeof n||(e.style.cssText=o||""),o&&"object"==typeof o){if("string"!=typeof n){ for(var i in n){ i in o||(e.style[i]=""); } }for(var i in o){ e.style[i]="number"==typeof o[i]&&!1===P.test(i)?o[i]+"px":o[i]; }}}else if("html"===t){ o&&(e.innerHTML=o); }else if("o"==t[0]&&"n"==t[1]){var l=t!==(t=t.replace(/Capture$/,""));t=t.toLowerCase().substring(2),o?n||e.addEventListener(t,s,l):e.removeEventListener(t,s,l),(e.__l||(e.__l={}))[t]=o;}else if("list"!==t&&"type"!==t&&!r&&t in e){ c(e,t,null==o?"":o),null!=o&&!1!==o||e.removeAttribute(t); }else{var a=r&&t!==(t=t.replace(/^xlink\:?/,""));null==o||!1===o?a?e.removeAttributeNS("http://www.w3.org/1999/xlink",t.toLowerCase()):e.removeAttribute(t):"function"!=typeof o&&(a?e.setAttributeNS("http://www.w3.org/1999/xlink",t.toLowerCase(),o):e.setAttribute(t,o));} }else { e.className=o||""; }}function c(e,t,n){try{e[t]=n;}catch(e){}}function s(e){return this.__l[e.type](e)}function f(){var e;while(e=E.pop()){ e.on_mounted&&e.on_mounted(); }}function d(e,t,n,o,r,i){D++||(H=null!=r&&void 0!==r.ownerSVGElement,R=null!=e&&!("__hyperattr_"in e));var l=h(e,t,n,o,i);return r&&l.parentNode!==r&&r.appendChild(l),--D||(R=!1,i||f()),l}function h(e,t,n,o,r){var i=e,a=H;if(null!=t&&"boolean"!=typeof t||(t=""),"string"==typeof t||"number"==typeof t){ return e&&void 0!==e.splitText&&e.parentNode&&(!e._component||r)?e.nodeValue!=t&&(e.nodeValue=t):(i=document.createTextNode(t),e&&(e.parentNode&&e.parentNode.replaceChild(i,e),m(e,!0))),i.__hyperattr_=!0,i; }var u=t.nodeName;if("function"==typeof u){ return k(e,t,n,o); }if(H="svg"===u||"foreignObject"!==u&&H,u+="",(!e||!l(e,u))&&(i=_(u,H),e)){while(e.firstChild){ i.appendChild(e.firstChild); }e.parentNode&&e.parentNode.replaceChild(i,e),m(e,!0);}var p=i.firstChild,c=i.__hyperattr_,s=t.children;if(null==c){c=i.__hyperattr_={};for(var f=i.attributes,d=f.length;d--;){ c[f[d].name]=f[d].value; }}return !R&&s&&1===s.length&&"string"==typeof s[0]&&null!=p&&void 0!==p.splitText&&null==p.nextSibling?p.nodeValue!=s[0]&&(p.nodeValue=s[0]):(s&&s.length||null!=p)&&v(i,s,n,o,R||null!=c.html),y(i,t.attributes,c),H=a,i}function v(e,t,n,o,r){var l,a,_,p,c,s=e.childNodes,f=[],d={},v=0,b=0,y=s.length,g=0,w=t?t.length:0;if(0!==y){ for(var C=0;C<y;C++){var x=s[C],N=x.__hyperattr_,k=w&&N?x._component?x._component.__k:N.key:null;null!=k?(v++,d[k]=x):(N||(void 0!==x.splitText?!r||x.nodeValue.trim():r))&&(f[g++]=x);} }if(0!==w){ for(var C=0;C<w;C++){p=t[C],c=null;var k=p.key;if(null!=k){ v&&void 0!==d[k]&&(c=d[k],d[k]=void 0,v--); }else if(!c&&b<g){ for(l=b;l<g;l++){ if(void 0!==f[l]&&i(a=f[l],p,r)){c=a,f[l]=void 0,l===g-1&&g--,l===b&&b++;break} } }c=h(c,p,n,o),_=s[C],c&&c!==e&&c!==_&&(null==_?e.appendChild(c):c===_.nextSibling?u(_):e.insertBefore(c,_));} }if(v){ for(var C in d){ void 0!==d[C]&&m(d[C],!1); } }while(b<=g){ void 0!==(c=f[g--])&&m(c,!1); }}function m(e,t){var n=e._component;n?U(n):(null!=e.__hyperattr_&&e.__hyperattr_.ref&&e.__hyperattr_.ref(null),!1!==t&&null!=e.__hyperattr_||u(e),b(e));}function b(e){e=e.lastChild;while(e){var t=e.previousSibling;m(e,!0),e=t;}}function y(e,t,n){var o;for(o in n){ t&&null!=t[o]||null==n[o]||p(e,o,n[o],n[o]=void 0,H); }for(o in t){ "children"===o||"innerHTML"===o||o in n&&t[o]===("value"===o||"checked"===o?e[o]:n[o])||p(e,o,n[o],n[o]=t[o],H); }}function g(e){var t=e.constructor.name;(j[t]||(j[t]=[])).push(e);}function w(e,t,n){var o,r=j[e.name];if(e.prototype&&e.prototype.render?(o=new e(t,n),L.call(o,t,n)):(o=new L(t,n),o.constructor=e,o.render=C),r){ for(var i=r.length;i--;){ if(r[i].constructor===e){o.__b=r[i].__b,r.splice(i,1);break} } }return o}function C(e,t,n){return this.constructor(e,n)}function x(e,t,n,r,i){e.__x||(e.__x=!0,(e.__r=t.ref)&&delete t.ref,(e.__k=t.key)&&delete t.key,!e.base||i?e.on_mount&&e.on_mount():e.componentWillReceiveProps&&e.componentWillReceiveProps(t,r),r&&r!==e.context&&(e.__c||(e.__c=e.context),e.context=r),e.__p||(e.__p=e.props),e.props=t,e.__x=!1,0!==n&&(1!==n&&!1===M.syncComponentUpdates&&e.base?o(e):N(e,1,i)),e.__r&&e.__r(e));}function N(e,t,o,r){if(!e.__x){var i,l,_,u=e.props,p=e.state,c=e.context,s=e.__p||u,h=e.__s||p,v=e.__c||c,b=e.base,y=e.__b,g=b||y,C=e._component,k=!1;if(b&&(e.props=s,e.state=h,e.context=v,2!==t&&e.shouldComponentUpdate&&!1===e.shouldComponentUpdate(u,p,c)?k=!0:e.componentWillUpdate&&e.componentWillUpdate(u,p,c),e.props=u,e.state=p,e.context=c),e.__p=e.__s=e.__c=e.__b=null,e.__d=!1,!k){i=e.render(u,p,c),e.getChildContext&&(c=n(n({},c),e.getChildContext()));var L,T,S=i&&i.nodeName;if("function"==typeof S){var W=a(i);l=C,l&&l.constructor===S&&W.key==l.__k?x(l,W,1,c,!1):(L=l,e._component=l=w(S,W,c),l.__b=l.__b||y,l.__u=e,x(l,W,0,c,!1),N(l,1,o,!0)),T=l.base;}else { _=g,L=C,L&&(_=e._component=null),(g||1===t)&&(_&&(_._component=null),T=d(_,i,c,o||!b,g&&g.parentNode,!0)); }if(g&&T!==g&&l!==C){var P=g.parentNode;P&&T!==P&&(P.replaceChild(T,g),L||(g._component=null,m(g,!1)));}if(L&&U(L),e.base=T,T&&!r){var V=e,A=e;while(A=A.__u){ (V=A).base=T; }T._component=V,T._componentConstructor=V.constructor;}}if(!b||o?E.unshift(e):k||(e.on_updated&&e.on_updated(s,h,v),M.afterUpdate&&M.afterUpdate(e)),null!=e.__h){ while(e.__h.length){ e.__h.pop().call(e); } }D||r||f();}}function k(e,t,n,o){var r=e&&e._component,i=r,l=e,_=r&&e._componentConstructor===t.nodeName,u=_,p=a(t);while(r&&!u&&(r=r.__u)){ u=r.constructor===t.nodeName; }return r&&u&&(!o||r._component)?(x(r,p,3,n,o),e=r.base):(i&&!_&&(U(i),e=l=null),r=w(t.nodeName,p,n),e&&!r.__b&&(r.__b=e,l=null),x(r,p,1,n,o),e=r.base,l&&e!==l&&(l._component=null,m(l,!1))),e}function U(e){var t=e.base;e.__x=!0,e.componentWillUnmount&&e.componentWillUnmount(),e.base=null;var n=e._component;n?U(n):t&&(t.__hyperattr_&&t.__hyperattr_.ref&&t.__hyperattr_.ref(null),e.__b=t,u(t),g(e),b(t)),e.__r&&e.__r(null);}function L(e,t){this.__d=!0,this.context=t,this.props=e,this.state=this.state||{};}function T(e,t,n){return d(n,e,{},!1,t,!1)}var M={},S=[],W=[],P=/acit|ex(?:s|g|n|p|$)|rph|ows|mnc|ntw|ine[ch]|zoo|^ord/i,V="function"==typeof Promise?Promise.resolve().then.bind(Promise.resolve()):setTimeout,A=[],E=[],D=0,H=!1,R=!1,j={};n(L.prototype,{set_state:function(e,t){var r=this.state;this.__s||(this.__s=n({},r)),n(r,"function"==typeof e?e(r,this.props):e),t&&(this.__h=this.__h||[]).push(t),o(this);},forceUpdate:function(e){e&&(this.__h=this.__h||[]).push(e),N(this,2);},render:function(){}});var I={h:t,Component:L,render:T};module.exports=I;}();
	});

	frappe.socketio = {
		open_tasks: {},
		open_docs: [],
		emit_queue: [],
		init: function(port) {
			if ( port === void 0 ) port = 3000;

			if (!window.io) {
				return;
			}

			if (frappe.boot.disable_async) {
				return;
			}

			if (frappe.socketio.socket) {
				return;
			}

			//Enable secure option when using HTTPS
			if (window.location.protocol == "https:") {
				frappe.socketio.socket = io.connect(frappe.socketio.get_host(port), {secure: true});
			}
			else if (window.location.protocol == "http:") {
				frappe.socketio.socket = io.connect(frappe.socketio.get_host(port));
			}
			else if (window.location.protocol == "file:") {
				frappe.socketio.socket = io.connect(window.localStorage.server);
			}

			if (!frappe.socketio.socket) {
				console.log("Unable to connect to " + frappe.socketio.get_host(port));
				return;
			}

			frappe.socketio.socket.on('msgprint', function(message) {
				frappe.msgprint(message);
			});

			frappe.socketio.socket.on('eval_js', function(message) {
				eval(message);
			});

			frappe.socketio.socket.on('progress', function(data) {
				if(data.progress) {
					data.percent = flt(data.progress[0]) / data.progress[1] * 100;
				}
				if(data.percent) {
					if(data.percent==100) {
						frappe.hide_progress();
					} else {
						frappe.show_progress(data.title || __("Progress"), data.percent, 100, data.description);
					}
				}
			});

			frappe.socketio.setup_listeners();
			frappe.socketio.setup_reconnect();
			frappe.socketio.uploader = new frappe.socketio.SocketIOUploader();

			$(document).on('form-load form-rename', function(e, frm) {
				if (frm.is_new()) {
					return;
				}

				for (var i=0, l=frappe.socketio.open_docs.length; i<l; i++) {
					var d = frappe.socketio.open_docs[i];
					if (frm.doctype==d.doctype && frm.docname==d.name) {
						// already subscribed
						return false;
					}
				}

				frappe.socketio.doc_subscribe(frm.doctype, frm.docname);
			});

			$(document).on("form-refresh", function(e, frm) {
				if (frm.is_new()) {
					return;
				}

				frappe.socketio.doc_open(frm.doctype, frm.docname);
			});

			$(document).on('form-unload', function(e, frm) {
				if (frm.is_new()) {
					return;
				}

				// frappe.socketio.doc_unsubscribe(frm.doctype, frm.docname);
				frappe.socketio.doc_close(frm.doctype, frm.docname);
			});

			window.onbeforeunload = function() {
				if (!cur_frm || cur_frm.is_new()) {
					return;
				}

				// if tab/window is closed, notify other users
				if (cur_frm.doc) {
					frappe.socketio.doc_close(cur_frm.doctype, cur_frm.docname);
				}
			};
		},
		get_host: function(port) {
			if ( port === void 0 ) port = 3000;

			var host = window.location.origin;
			if(window.dev_server) {
				var parts = host.split(":");
				port = frappe.boot.socketio_port || port.toString() || '3000';
				if(parts.length > 2) {
					host = parts[0] + ":" + parts[1];
				}
				host = host + ":" + port;
			}
			return host;
		},
		subscribe: function(task_id, opts) {
			// TODO DEPRECATE

			frappe.socketio.socket.emit('task_subscribe', task_id);
			frappe.socketio.socket.emit('progress_subscribe', task_id);

			frappe.socketio.open_tasks[task_id] = opts;
		},
		task_subscribe: function(task_id) {
			frappe.socketio.socket.emit('task_subscribe', task_id);
		},
		task_unsubscribe: function(task_id) {
			frappe.socketio.socket.emit('task_unsubscribe', task_id);
		},
		doc_subscribe: function(doctype, docname) {
			if (frappe.flags.doc_subscribe) {
				console.log('throttled');
				return;
			}

			frappe.flags.doc_subscribe = true;

			// throttle to 1 per sec
			setTimeout(function() { frappe.flags.doc_subscribe = false; }, 1000);

			frappe.socketio.socket.emit('doc_subscribe', doctype, docname);
			frappe.socketio.open_docs.push({doctype: doctype, docname: docname});
		},
		doc_unsubscribe: function(doctype, docname) {
			frappe.socketio.socket.emit('doc_unsubscribe', doctype, docname);
			frappe.socketio.open_docs = $.filter(frappe.socketio.open_docs, function(d) {
				if(d.doctype===doctype && d.name===docname) {
					return null;
				} else {
					return d;
				}
			});
		},
		doc_open: function(doctype, docname) {
			// notify that the user has opened this doc, if not already notified
			if(!frappe.socketio.last_doc
				|| (frappe.socketio.last_doc[0]!=doctype && frappe.socketio.last_doc[0]!=docname)) {
				frappe.socketio.socket.emit('doc_open', doctype, docname);
			}
			frappe.socketio.last_doc = [doctype, docname];
		},
		doc_close: function(doctype, docname) {
			// notify that the user has closed this doc
			frappe.socketio.socket.emit('doc_close', doctype, docname);
		},

		setup_listeners: function() {
			frappe.socketio.socket.on('task_status_change', function(data) {
				frappe.socketio.process_response(data, data.status.toLowerCase());
			});
			frappe.socketio.socket.on('task_progress', function(data) {
				frappe.socketio.process_response(data, "progress");
			});
		},
		setup_reconnect: function() {
			// subscribe again to open_tasks
			frappe.socketio.socket.on("connect", function() {
				// wait for 5 seconds before subscribing again
				// because it takes more time to start python server than nodejs server
				// and we use validation requests to python server for subscribing
				setTimeout(function() {
					$.each(frappe.socketio.open_tasks, function(task_id, opts) {
						frappe.socketio.subscribe(task_id, opts);
					});

					// re-connect open docs
					$.each(frappe.socketio.open_docs, function(d) {
						if(locals[d.doctype] && locals[d.doctype][d.name]) {
							frappe.socketio.doc_subscribe(d.doctype, d.name);
						}
					});

					if (cur_frm && cur_frm.doc) {
						frappe.socketio.doc_open(cur_frm.doc.doctype, cur_frm.doc.name);
					}
				}, 5000);
			});
		},
		process_response: function(data, method) {
			if(!data) {
				return;
			}

			// success
			var opts = frappe.socketio.open_tasks[data.task_id];
			if(opts[method]) {
				opts[method](data);
			}

			// "callback" is std frappe term
			if(method==="success") {
				if(opts.callback) { opts.callback(data); }
			}

			// always
			frappe.request.cleanup(opts, data);
			if(opts.always) {
				opts.always(data);
			}

			// error
			if(data.status_code && data.status_code > 400 && opts.error) {
				opts.error(data);
			}
		}
	};

	frappe.provide("frappe.realtime");
	frappe.realtime.on = function(event, callback) {
		frappe.socketio.socket && frappe.socketio.socket.on(event, callback);
	};

	frappe.realtime.off = function(event, callback) {
		frappe.socketio.socket && frappe.socketio.socket.off(event, callback);
	};

	frappe.realtime.publish = function(event, message) {
		if(frappe.socketio.socket) {
			frappe.socketio.socket.emit(event, message);
		}
	};

	frappe.socketio.SocketIOUploader = class SocketIOUploader {
		constructor() {
		var this$1 = this;

			frappe.socketio.socket.on('upload-request-slice', function (data) {
				var place = data.currentSlice * this$1.chunk_size,
					slice = this$1.file.slice(place,
						place + Math.min(this$1.chunk_size, this$1.file.size - place));

				if (this$1.on_progress) {
					// update progress
					this$1.on_progress(place / this$1.file.size * 100);
				}

				this$1.reader.readAsArrayBuffer(slice);
				this$1.started = true;
				this$1.keep_alive();
			});

			frappe.socketio.socket.on('upload-end', function (data) {
				this$1.reader = null;
				this$1.file = null;
				if (data.file_url.substr(0, 7)==='/public') {
					data.file_url = data.file_url.substr(7);
				}
				this$1.callback(data);
			});

			frappe.socketio.socket.on('upload-error', function (data) {
				this$1.disconnect(false);
				frappe.msgprint({
					title: __('Upload Failed'),
					message: data.error,
					indicator: 'red'
				});
			});

			frappe.socketio.socket.on('disconnect', function () {
				this$1.disconnect();
			});
		}

		start(ref) {
			var this$1 = this;
			if ( ref === void 0 ) ref = {};
			var file = ref.file; if ( file === void 0 ) file = null;
			var is_private = ref.is_private; if ( is_private === void 0 ) is_private = 0;
			var filename = ref.filename; if ( filename === void 0 ) filename = '';
			var callback = ref.callback; if ( callback === void 0 ) callback = null;
			var on_progress = ref.on_progress; if ( on_progress === void 0 ) on_progress = null;
			var chunk_size = ref.chunk_size; if ( chunk_size === void 0 ) chunk_size = 24576;
			var fallback = ref.fallback; if ( fallback === void 0 ) fallback = null;


			if (this.reader) {
				frappe.throw(__('File Upload in Progress. Please try again in a few moments.'));
			}

			function fallback_required() {
				return !frappe.boot.sysdefaults.use_socketio_to_upload_file || !frappe.socketio.socket.connected;
			}

			if (fallback_required()) {
				return fallback ? fallback() : frappe.throw(__('Socketio is not connected. Cannot upload'));
			}

			this.reader = new FileReader();
			this.file = file;
			this.chunk_size = chunk_size;
			this.callback = callback;
			this.on_progress = on_progress;
			this.fallback = fallback;
			this.started = false;

			this.reader.onload = function () {
				frappe.socketio.socket.emit('upload-accept-slice', {
					is_private: is_private,
					name: filename,
					type: this$1.file.type,
					size: this$1.file.size,
					data: this$1.reader.result
				});
				this$1.keep_alive();
			};

			var slice = file.slice(0, this.chunk_size);
			this.reader.readAsArrayBuffer(slice);
		}

		keep_alive() {
			var this$1 = this;

			if (this.next_check) {
				clearTimeout (this.next_check);
			}
			this.next_check = setTimeout (function () {
				if (!this$1.started) {
					// upload never started, so try fallback
					if (this$1.fallback) {
						this$1.fallback();
					} else {
						this$1.disconnect();
					}
				}
				this$1.disconnect();
			}, 3000);
		}

		disconnect(with_message) {
			if ( with_message === void 0 ) with_message = true;

			if (this.reader) {
				this.reader = null;
				this.file = null;
				frappe.hide_progress();
				if (with_message) {
					frappe.msgprint({
						title: __('File Upload'),
						message: __('File Upload Disconnected. Please try again.'),
						indicator: 'red'
					});
				}
			}
		}
	};

	// Copyright (c) 2015, Frappe Technologies Pvt. Ltd. and Contributors
	// MIT License. See license.txt

	/*

	Inheritence "Class"
	-------------------
	see: http://ejohn.org/blog/simple-javascript-inheritance/
	To subclass, use:

		var MyClass = Class.extend({
			init: function
		})

	*/
	// https://stackoverflow.com/a/15052240/5353542

	/* Simple JavaScript Inheritance for ES 5.1
	 * based on http://ejohn.org/blog/simple-javascript-inheritance/
	 *  (inspired by base2 and Prototype)
	 * MIT Licensed.
	 */
	(function(global) {
		var fnTest = /xyz/.test(function(){}) ? /\b_super\b/ : /.*/;

		// The base Class implementation (does nothing)
		function Class(){}

		// Create a new Class that inherits from this class
		Class.extend = function(props) {
		  var _super = this.prototype;

		  // Set up the prototype to inherit from the base class
		  // (but without running the init constructor)
		  var proto = Object.create(_super);

		  // Copy the properties over onto the new prototype
		  for (var name in props) {
			// Check if we're overwriting an existing function
			proto[name] = typeof props[name] === "function" &&
			  typeof _super[name] == "function" && fnTest.test(props[name])
			  ? (function(name, fn){
				  return function() {
					var tmp = this._super;

					// Add a new ._super() method that is the same method
					// but on the super-class
					this._super = _super[name];

					// The method only need to be bound temporarily, so we
					// remove it when we're done executing
					var ret = fn.apply(this, arguments);
					this._super = tmp;

					return ret;
				  };
				})(name, props[name])
			  : props[name];
		  }

		  // The new constructor
		  var newClass = typeof proto.init === "function"
			? proto.hasOwnProperty("init")
			  ? proto.init // All construction is actually done in the init method
			  : function SubClass(){ _super.init.apply(this, arguments); }
			: function EmptyClass(){};

		  // Populate our constructed prototype object
		  newClass.prototype = proto;

		  // Enforce the constructor to be what we expect
		  proto.constructor = newClass;

		  // And make this class extendable
		  newClass.extend = Class.extend;

		  return newClass;
		};

		// export
		global.Class = Class;
	  })(commonjsGlobal);

	frappe.ui.form.Layout = Class.extend({
		init: function(opts) {
			this.views = {};
			this.pages = [];
			this.sections = [];
			this.fields_list = [];
			this.fields_dict = {};

			$.extend(this, opts);
		},
		make: function() {
			if(!this.parent && this.body) {
				this.parent = this.body;
			}
			this.wrapper = $('<div class="form-layout">').appendTo(this.parent);
			this.message = $('<div class="form-message text-muted small hidden"></div>').appendTo(this.wrapper);
			if(!this.fields) {
				this.fields = this.get_doctype_fields();
			}
			this.setup_tabbing();
			this.render();
		},
		show_empty_form_message: function() {
			if(!(this.wrapper.find(".frappe-control:visible").length || this.wrapper.find(".section-head.collapsed").length)) {
				this.show_message(__("This form does not have any input"));
			}
		},
		get_doctype_fields: function() {
			var fields = [
				{
					parent: this.frm.doctype,
					fieldtype: 'Data',
					fieldname: '__newname',
					reqd: 1,
					hidden: 1,
					label: __('Name'),
					get_status: function(field) {
						if (field.frm && field.frm.is_new()
							&& field.frm.meta.autoname
							&& ['prompt', 'name'].includes(field.frm.meta.autoname.toLowerCase())) {
							return 'Write';
						}
						return 'None';
					}
				}
			];
			fields = fields.concat(frappe.meta.sort_docfields(frappe.meta.docfield_map[this.doctype]));
			return fields;
		},
		show_message: function(html, color) {
			if (this.message_color) {
				// remove previous color
				this.message.removeClass(this.message_color);
			}
			this.message_color = (color && ['yellow', 'blue'].includes(color)) ? color : 'blue';
			if(html) {
				if(html.substr(0, 1)!=='<') {
					// wrap in a block
					html = '<div>' + html + '</div>';
				}
				this.message.removeClass('hidden').addClass(this.message_color);
				$(html).appendTo(this.message);
			} else {
				this.message.empty().addClass('hidden');
			}
		},
		render: function(new_fields) {
			var me = this;
			var fields = new_fields || this.fields;

			this.section = null;
			this.column = null;

			if (this.with_dashboard) {
				this.setup_dashboard_section();
			}

			if (this.no_opening_section()) {
				this.make_section();
			}
			$.each(fields, function(i, df) {
				switch(df.fieldtype) {
					case "Fold":
						me.make_page(df);
						break;
					case "Section Break":
						me.make_section(df);
						break;
					case "Column Break":
						me.make_column(df);
						break;
					default:
						me.make_field(df);
				}
			});

		},

		no_opening_section: function() {
			return (this.fields[0] && this.fields[0].fieldtype!="Section Break") || !this.fields.length;
		},

		setup_dashboard_section: function() {
			if (this.no_opening_section()) {
				this.fields.unshift({fieldtype: 'Section Break'});
			}

			this.fields.unshift({
				fieldtype: 'Section Break',
				fieldname: '_form_dashboard',
				label: __('Dashboard'),
				cssClass: 'form-dashboard',
				collapsible: 1,
				//hidden: 1
			});
		},

		replace_field: function(fieldname, df, render) {
			df.fieldname = fieldname; // change of fieldname is avoided
			if (this.fields_dict[fieldname] && this.fields_dict[fieldname].df) {
				var fieldobj = this.init_field(df, render);
				this.fields_dict[fieldname].$wrapper.remove();
				this.fields_list.splice(this.fields_dict[fieldname], 1, fieldobj);
				this.fields_dict[fieldname] = fieldobj;
				if (this.frm) {
					fieldobj.perm = this.frm.perm;
				}
				this.section.fields_list.splice(this.section.fields_dict[fieldname], 1, fieldobj);
				this.section.fields_dict[fieldname] = fieldobj;
				this.refresh_fields([df]);
			}
		},

		make_field: function(df, colspan, render) {
			!this.section && this.make_section();
			!this.column && this.make_column();

			var fieldobj = this.init_field(df, render);
			this.fields_list.push(fieldobj);
			this.fields_dict[df.fieldname] = fieldobj;
			if(this.frm) {
				fieldobj.perm = this.frm.perm;
			}

			this.section.fields_list.push(fieldobj);
			this.section.fields_dict[df.fieldname] = fieldobj;
		},

		init_field: function(df, render) {
			if ( render === void 0 ) render = false;

			var fieldobj = frappe.ui.form.make_control({
				df: df,
				doctype: this.doctype,
				parent: this.column.wrapper.get(0),
				frm: this.frm,
				render_input: render
			});

			fieldobj.layout = this;
			return fieldobj;
		},

		make_page: function(df) {
			var me = this,
				head = $('<div class="form-clickable-section text-center">\
				<a class="btn-fold h6 text-muted">'+__("Show more details")+'</a>\
			</div>').appendTo(this.wrapper);

			this.page = $('<div class="form-page second-page hide"></div>').appendTo(this.wrapper);

			this.fold_btn = head.find(".btn-fold").on("click", function() {
				var page = $(this).parent().next();
				if(page.hasClass("hide")) {
					$(this).removeClass("btn-fold").html(__("Hide details"));
					page.removeClass("hide");
					frappe.utils.scroll_to($(this), true, 30);
					me.folded = false;
				} else {
					$(this).addClass("btn-fold").html(__("Show more details"));
					page.addClass("hide");
					me.folded = true;
				}
			});

			this.section = null;
			this.folded = true;
		},

		unfold: function() {
			this.fold_btn.trigger('click');
		},

		make_section: function(df) {
			this.section = new frappe.ui.form.Section(this, df);

			// append to layout fields
			if(df) {
				this.fields_dict[df.fieldname] = this.section;
				this.fields_list.push(this.section);
			}

			this.column = null;
		},

		make_column: function(df) {
			this.column = new frappe.ui.form.Column(this.section, df);
			if(df && df.fieldname) {
				this.fields_list.push(this.column);
			}
		},

		refresh: function(doc) {
			var me = this;
			if(doc) { this.doc = doc; }

			if (this.frm) {
				this.wrapper.find(".empty-form-alert").remove();
			}

			// NOTE this might seem redundant at first, but it needs to be executed when frm.refresh_fields is called
			me.attach_doc_and_docfields(true);

			if(this.frm && this.frm.wrapper) {
				$(this.frm.wrapper).trigger("refresh-fields");
			}

			// dependent fields
			this.refresh_dependency();

			// refresh sections
			this.refresh_sections();

			// collapse sections
			if(this.frm) {
				this.refresh_section_collapse();
			}
		},

		refresh_sections: function() {
			var cnt = 0;

			// hide invisible sections and set alternate background color
			this.wrapper.find(".form-section:not(.hide-control)").each(function() {
				var $this = $(this).removeClass("empty-section")
					.removeClass("visible-section")
					.removeClass("shaded-section");
				if(!$this.find(".frappe-control:not(.hide-control)").length
					&& !$this.hasClass('form-dashboard')) {
					// nothing visible, hide the section
					$this.addClass("empty-section");
				} else {
					$this.addClass("visible-section");
					if(cnt % 2) {
						$this.addClass("shaded-section");
					}
					cnt++;
				}
			});
		},

		refresh_fields: function(fields) {
			var fieldnames = fields.map(function (field) {
				if(field.fieldname) { return field.fieldname; }
			});

			this.fields_list.map(function (fieldobj) {
				if(fieldnames.includes(fieldobj.df.fieldname)) {
					fieldobj.refresh();
					if(fieldobj.df["default"]) {
						fieldobj.set_input(fieldobj.df["default"]);
					}
				}
			});
		},

		add_fields: function(fields) {
			this.render(fields);
			this.refresh_fields(fields);
		},

		refresh_section_collapse: function() {
			if(!this.doc) { return; }

			for(var i=0; i<this.sections.length; i++) {
				var section = this.sections[i];
				var df = section.df;
				if(df && df.collapsible) {
					var collapse = true;

					if(df.collapsible_depends_on) {
						collapse = !this.evaluate_depends_on_value(df.collapsible_depends_on);
					}

					if (collapse && section.has_missing_mandatory()) {
						collapse = false;
					}

					if(df.fieldname === '_form_dashboard') {
						collapse = localStorage.getItem('collapseFormDashboard')==='yes' ? true : false;
					}

					section.collapse(collapse);
				}
			}
		},

		attach_doc_and_docfields: function(refresh) {
			var me = this;
			for(var i=0, l=this.fields_list.length; i<l; i++) {
				var fieldobj = this.fields_list[i];
				if(me.doc) {
					fieldobj.doc = me.doc;
					fieldobj.doctype = me.doc.doctype;
					fieldobj.docname = me.doc.name;
					fieldobj.df = frappe.meta.get_docfield(me.doc.doctype,
						fieldobj.df.fieldname, me.frm ? me.frm.doc.name : me.doc.name) || fieldobj.df;

					// on form change, permissions can change
					if(me.frm) {
						fieldobj.perm = me.frm.perm;
					}
				}
				refresh && fieldobj.df && fieldobj.refresh && fieldobj.refresh();
			}
		},

		refresh_section_count: function() {
			this.wrapper.find(".section-count-label:visible").each(function(i) {
				$(this).html(i+1);
			});
		},
		setup_tabbing: function() {
			var me = this;
			this.wrapper.on("keydown", function(ev) {
				if(ev.which==9) {
					var current = $(ev.target),
						doctype = current.attr("data-doctype"),
						fieldname = current.attr("data-fieldname");
					if(doctype)
						{ return me.handle_tab(doctype, fieldname, ev.shiftKey); }
				}
			});
		},
		handle_tab: function(doctype, fieldname, shift) {
			var me = this,
				grid_row = null,
				prev = null,
				fields = me.fields_list,
				focused = false;

			// in grid
			if(doctype != me.doctype) {
				grid_row = me.get_open_grid_row();
				if(!grid_row || !grid_row.layout) {
					return;
				}
				fields = grid_row.layout.fields_list;
			}

			for(var i=0, len=fields.length; i < len; i++) {
				if(fields[i].df.fieldname==fieldname) {
					if(shift) {
						if(prev) {
							this.set_focus(prev);
						} else {
							$(this.primary_button).focus();
						}
						break;
					}
					if(i < len-1) {
						focused = me.focus_on_next_field(i, fields);
					}

					if (focused) {
						break;
					}
				}
				if(this.is_visible(fields[i]))
					{ prev = fields[i]; }
			}

			if (!focused) {
				// last field in this group
				if(grid_row) {
					// in grid
					if(grid_row.doc.idx==grid_row.grid.grid_rows.length) {
						// last row, close it and find next field
						grid_row.toggle_view(false, function() {
							grid_row.grid.frm.layout.handle_tab(grid_row.grid.df.parent, grid_row.grid.df.fieldname);
						});
					} else {
						// next row
						grid_row.grid.grid_rows[grid_row.doc.idx].toggle_view(true);
					}
				} else {
					$(this.primary_button).focus();
				}
			}

			return false;
		},
		focus_on_next_field: function(start_idx, fields) {
			// loop to find next eligible fields
			for(var i= start_idx + 1, len = fields.length; i < len; i++) {
				var field = fields[i];
				if(this.is_visible(field)) {
					if(field.df.fieldtype==="Table") {
						// open table grid
						if(!(field.grid.grid_rows && field.grid.grid_rows.length)) {
							// empty grid, add a new row
							field.grid.add_new_row();
						}
						// show grid row (if exists)
						field.grid.grid_rows[0].show_form();
						return true;

					} else if(!in_list(frappe.model.no_value_type, field.df.fieldtype)) {
						this.set_focus(field);
						return true;
					}
				}
			}
		},
		is_visible: function(field) {
			return field.disp_status==="Write" && (field.$wrapper && field.$wrapper.is(":visible"));
		},
		set_focus: function(field) {
			// next is table, show the table
			if(field.df.fieldtype=="Table") {
				if(!field.grid.grid_rows.length) {
					field.grid.add_new_row(1);
				} else {
					field.grid.grid_rows[0].toggle_view(true);
				}
			} else if(field.editor) {
				field.editor.set_focus();
			} else if(field.$input) {
				field.$input.focus();
			}
		},
		get_open_grid_row: function() {
			return $(".grid-row-open").data("grid_row");
		},
		refresh_dependency: function() {
			// Resolve "depends_on" and show / hide accordingly
			var me = this;

			// build dependants' dictionary
			var has_dep = false;

			for(var fkey in this.fields_list) {
				var f = this.fields_list[fkey];
				f.dependencies_clear = true;
				if(f.df.depends_on) {
					has_dep = true;
				}
			}

			if(!has_dep){ return; }

			// show / hide based on values
			for(var i=me.fields_list.length-1;i>=0;i--) {
				var f = me.fields_list[i];
				f.guardian_has_value = true;
				if(f.df.depends_on) {
					// evaluate guardian

					f.guardian_has_value = this.evaluate_depends_on_value(f.df.depends_on);

					// show / hide
					if(f.guardian_has_value) {
						if(f.df.hidden_due_to_dependency) {
							f.df.hidden_due_to_dependency = false;
							f.refresh();
						}
					} else {
						if(!f.df.hidden_due_to_dependency) {
							f.df.hidden_due_to_dependency = true;
							f.refresh();
						}
					}
				}
			}

			this.refresh_section_count();
		},
		evaluate_depends_on_value: function(expression) {
			var out = null;
			var doc = this.doc;

			if (!doc && this.get_values) {
				var doc = this.get_values(true);
			}

			if (!doc) {
				return;
			}

			var parent = this.frm ? this.frm.doc : null;

			if(typeof(expression) === 'boolean') {
				out = expression;

			} else if(typeof(expression) === 'function') {
				out = expression(doc);

			} else if(expression.substr(0,5)=='eval:') {
				try {
					out = eval(expression.substr(5));
					if(parent && parent.istable && expression.includes('is_submittable')) {
						out = true;
					}
				} catch(e) {
					frappe.throw(__('Invalid "depends_on" expression'));
				}

			} else if(expression.substr(0,3)=='fn:' && this.frm) {
				out = this.frm.script_manager.trigger(expression.substr(3), this.doctype, this.docname);
			} else {
				var value = doc[expression];
				if($.isArray(value)) {
					out = !!value.length;
				} else {
					out = !!value;
				}
			}

			return out;
		}
	});

	frappe.ui.form.Section = Class.extend({
		init: function(layout, df) {
			this.layout = layout;
			this.df = df || {};
			this.fields_list = [];
			this.fields_dict = {};

			this.make();
			// if(this.frm)
			// 	this.section.body.css({"padding":"0px 3%"})
			this.row = {
				wrapper: this.wrapper
			};

			if (this.df.collapsible && this.df.fieldname !== '_form_dashboard') {
				this.collapse(true);
			}

			this.refresh();
		},
		make: function() {
			if(!this.layout.page) {
				this.layout.page = $('<div class="form-page"></div>').appendTo(this.layout.wrapper);
			}

			this.wrapper = $('<div class="row form-section">')
				.appendTo(this.layout.page);
			this.layout.sections.push(this);

			if(this.df) {
				if(this.df.label) {
					this.make_head();
				}
				if(this.df.description) {
					$('<div class="col-sm-12 small text-muted form-section-description">' + __(this.df.description) + '</div>')
						.appendTo(this.wrapper);
				}
				if(this.df.cssClass) {
					this.wrapper.addClass(this.df.cssClass);
				}
			}


			// for bc
			this.body = $('<div class="section-body">').appendTo(this.wrapper);
		},
		make_head: function() {
			var me = this;
			if(!this.df.collapsible) {
				$('<div class="col-sm-12"><h6 class="form-section-heading uppercase">'
					+ __(this.df.label) + '</h6></div>')
					.appendTo(this.wrapper);
			} else {
				this.head = $('<div class="section-head"><a class="h6 uppercase">'
					+__(this.df.label)+'</a><span class="octicon octicon-chevron-down collapse-indicator"></span></div>').appendTo(this.wrapper);

				// show / hide based on status
				this.collapse_link = this.head.on("click", function() {
					me.collapse();
				});

				this.indicator = this.head.find(".collapse-indicator");
			}
		},
		refresh: function() {
			if(!this.df)
				{ return; }

			// hide if explictly hidden
			var hide = this.df.hidden || this.df.hidden_due_to_dependency;

			// hide if no perm
			if(!hide && this.layout && this.layout.frm && !this.layout.frm.get_perm(this.df.permlevel || 0, "read")) {
				hide = true;
			}

			this.wrapper.toggleClass("hide-control", !!hide);
		},
		collapse: function(hide) {
			// unknown edge case
			if (!(this.head && this.body)) {
				return;
			}

			if(hide===undefined) {
				hide = !this.body.hasClass("hide");
			}

			if (this.df.fieldname==='_form_dashboard') {
				localStorage.setItem('collapseFormDashboard', hide ? 'yes' : 'no');
			}

			this.body.toggleClass("hide", hide);
			this.head.toggleClass("collapsed", hide);
			this.indicator.toggleClass("octicon-chevron-down", hide);
			this.indicator.toggleClass("octicon-chevron-up", !hide);

			// refresh signature fields
			this.fields_list.forEach(function (f) {
				if (f.df.fieldtype=='Signature') {
					f.refresh();
				}
			});


		},
		has_missing_mandatory: function() {
			var missing_mandatory = false;
			for (var j=0, l=this.fields_list.length; j < l; j++) {
				var section_df = this.fields_list[j].df;
				if (section_df.reqd && this.layout.doc[section_df.fieldname]==null) {
					missing_mandatory = true;
					break;
				}
			}
			return missing_mandatory;
		}
	});

	frappe.ui.form.Column = Class.extend({
		init: function(section, df) {
			if(!df) { df = {}; }

			this.df = df;
			this.section = section;
			this.make();
			this.resize_all_columns();
		},
		make: function() {
			this.wrapper = $('<div class="form-column">\
			<form>\
			</form>\
		</div>').appendTo(this.section.body)
				.find("form")
				.on("submit", function() {
					return false;
				});

			if (this.df.label) {
				$('<label class="control-label">' + __(this.df.label)
					+ '</label>').appendTo(this.wrapper);
			}
		},
		resize_all_columns: function() {
			// distribute all columns equally
			var colspan = cint(12 / this.section.wrapper.find(".form-column").length);

			this.section.wrapper.find(".form-column").removeClass()
				.addClass("form-column")
				.addClass("col-sm-" + colspan);

		},
		refresh: function() {
			this.section.refresh();
		}
	});

	frappe.provide('frappe.ui');

	frappe.ui.FieldGroup = frappe.ui.form.Layout.extend({
		init: function(opts) {
			$.extend(this, opts);
			this.dirty = false;
			this._super();
			$.each(this.fields || [], function(i, f) {
				if(!f.fieldname && f.label) {
					f.fieldname = f.label.replace(/ /g, "_").toLowerCase();
				}
			});
			if(this.values) {
				this.set_values(this.values);
			}
		},
		make: function() {
			var this$1 = this;

			var me = this;
			if(this.fields) {
				this._super();
				this.refresh();
				// set default
				$.each(this.fields_list, function(i, field) {
					if (field.df["default"]) {
						var def_value = field.df["default"];

						if (def_value == 'Today' && field.df["fieldtype"] == 'Date') {
							def_value = frappe.datetime.get_today();
						}

						field.set_input(def_value);
						// if default and has depends_on, render its fields.
						me.refresh_dependency();
					}
				});

				if(!this.no_submit_on_enter) {
					this.catch_enter_as_submit();
				}

				$(this.wrapper).find('input, select').on('change', function () {
					this$1.dirty = true;

					frappe.run_serially([
						function () { return frappe.timeout(0.1); },
						function () { return me.refresh_dependency(); }
					]);
				});

			}
		},
		first_button: false,
		focus_on_first_input: function() {
			if(this.no_focus) { return; }
			$.each(this.fields_list, function(i, f) {
				if(!in_list(['Date', 'Datetime', 'Time', 'Check'], f.df.fieldtype) && f.set_focus) {
					f.set_focus();
					return false;
				}
			});
		},
		catch_enter_as_submit: function() {
			var me = this;
			$(this.body).find('input[type="text"], input[type="password"]').keypress(function(e) {
				if(e.which==13) {
					if(me.has_primary_action) {
						e.preventDefault();
						me.get_primary_btn().trigger("click");
					}
				}
			});
		},
		get_input: function(fieldname) {
			var field = this.fields_dict[fieldname];
			return $(field.txt ? field.txt : field.input);
		},
		get_field: function(fieldname) {
			return this.fields_dict[fieldname];
		},
		get_values: function(ignore_errors) {
			var ret = {};
			var errors = [];
			for(var key in this.fields_dict) {
				var f = this.fields_dict[key];
				if(f.get_value) {
					var v = f.get_value();
					if(f.df.reqd && is_null(v))
						{ errors.push(__(f.df.label)); }

					if(!is_null(v)) { ret[f.df.fieldname] = v; }
				}
			}
			if(errors.length && !ignore_errors) {
				frappe.msgprint({
					title: __('Missing Values Required'),
					message: __('Following fields have missing values:') +
						'<br><br><ul><li>' + errors.join('<li>') + '</ul>',
					indicator: 'orange'
				});
				return null;
			}
			return ret;
		},
		get_value: function(key) {
			var f = this.fields_dict[key];
			return f && (f.get_value ? f.get_value() : null);
		},
		set_value: function(key, val){
			var this$1 = this;

			return new Promise(function (resolve) {
				var f = this$1.fields_dict[key];
				if(f) {
					f.set_value(val).then(function () {
						f.set_input(val);
						this$1.refresh_dependency();
						resolve();
					});
				} else {
					resolve();
				}
			});
		},
		set_input: function(key, val) {
			return this.set_value(key, val);
		},
		set_values: function(dict) {
			for(var key in dict) {
				if(this.fields_dict[key]) {
					this.set_value(key, dict[key]);
				}
			}
		},
		clear: function() {
			for(var key in this.fields_dict) {
				var f = this.fields_dict[key];
				if(f && f.set_input) {
					f.set_input(f.df['default'] || '');
				}
			}
		},
		set_df_property: function (fieldname, prop, value) {
			var field    = this.get_field(fieldname);
			field.df[prop] = value;
			field.refresh();
		}
	});

	// Copyright (c) 2015, Frappe Technologies Pvt. Ltd. and Contributors
	// MIT License. See license.txt

	// add a new dom element
	frappe.provide('frappe.dom');

	frappe.dom = {
		id_count: 0,
		freeze_count: 0,
		by_id: function(id) {
			return document.getElementById(id);
		},
		get_unique_id: function() {
			var id = 'unique-' + frappe.dom.id_count;
			frappe.dom.id_count++;
			return id;
		},
		set_unique_id: function(ele) {
			var $ele = $(ele);
			if($ele.attr('id')) {
				return $ele.attr('id');
			}
			var id = 'unique-' + frappe.dom.id_count;
			$ele.attr('id', id);
			frappe.dom.id_count++;
			return id;
		},
		eval: function(txt) {
			if(!txt) { return; }
			var el = document.createElement('script');
			el.appendChild(document.createTextNode(txt));
			// execute the script globally
			document.getElementsByTagName('head')[0].appendChild(el);
		},
		remove_script_and_style: function(txt) {
			var evil_tags = ["script", "style", "noscript", "title", "meta", "base", "head"];
			var regex = new RegExp(evil_tags.map(function (tag) { return ("<" + tag + ">.*<\\/" + tag + ">"); }).join('|'));
			if (!regex.test(txt)) {
				// no evil tags found, skip the DOM method entirely!
				return txt;
			}

			var div = document.createElement('div');
			div.innerHTML = txt;
			var found = false;
			evil_tags.forEach(function(e) {
				var elements = div.getElementsByTagName(e);
				i = elements.length;
				while (i--) {
					found = true;
					elements[i].parentNode.removeChild(elements[i]);
				}
			});

			// remove links with rel="stylesheet"
			var elements = div.getElementsByTagName('link');
			var i = elements.length;
			while (i--) {
				if (elements[i].getAttribute("rel")=="stylesheet"){
					found = true;
					elements[i].parentNode.removeChild(elements[i]);
				}
			}
			if(found) {
				return div.innerHTML;
			} else {
				// don't disturb
				return txt;
			}
		},
		is_element_in_viewport: function (el, tolerance) {
			if ( tolerance === void 0 ) tolerance=0;


			//special bonus for those using jQuery
			if (typeof jQuery === "function" && el instanceof jQuery) {
				el = el[0];
			}

			var rect = el.getBoundingClientRect();

			return (
				rect.top + tolerance >= 0
				&& rect.left + tolerance >= 0
				&& rect.bottom - tolerance <= $(window).height()
				&& rect.right - tolerance <= $(window).width()
			);
		},

		set_style: function(txt, id) {
			if(!txt) { return; }

			var se = document.createElement('style');
			se.type = "text/css";

			if (id) {
				var element = document.getElementById(id);
				if (element) {
					element.parentNode.removeChild(element);
				}
				se.id = id;
			}

			if (se.styleSheet) {
				se.styleSheet.cssText = txt;
			} else {
				se.appendChild(document.createTextNode(txt));
			}
			document.getElementsByTagName('head')[0].appendChild(se);
			return se;
		},
		add: function(parent, newtag, className, cs, innerHTML, onclick) {
			if(parent && parent.substr){ parent = frappe.dom.by_id(parent); }
			var c = document.createElement(newtag);
			if(parent)
				{ parent.appendChild(c); }

			// if image, 3rd parameter is source
			if(className) {
				if(newtag.toLowerCase()=='img')
					{ c.src = className; }
				else
					{ c.className = className; }
			}
			if(cs) { frappe.dom.css(c,cs); }
			if(innerHTML) { c.innerHTML = innerHTML; }
			if(onclick) { c.onclick = onclick; }
			return c;
		},
		css: function(ele, s) {
			if(ele && s) {
				$.extend(ele.style, s);
			}
			return ele;
		},
		activate: function($parent, $child, common_class, active_class) {
			if ( active_class === void 0 ) active_class='active';

			$parent.find(("." + common_class + "." + active_class))
				.removeClass(active_class);
			$child.addClass(active_class);
		},
		freeze: function(msg, css_class) {
			// blur
			if(!$('#freeze').length) {
				var freeze = $('<div id="freeze" class="modal-backdrop fade"></div>')
					.on("click", function() {
						if (cur_frm && cur_frm.cur_grid) {
							cur_frm.cur_grid.toggle_view();
							return false;
						}
					})
					.appendTo("#body_div");

				freeze.html(repl('<div class="freeze-message-container"><div class="freeze-message"><p class="lead">%(msg)s</p></div></div>',
					{msg: msg || ""}));

				setTimeout(function() { freeze.addClass("in"); }, 1);

			} else {
				$("#freeze").addClass("in");
			}

			if (css_class) {
				$("#freeze").addClass(css_class);
			}

			frappe.dom.freeze_count++;
		},
		unfreeze: function() {
			if(!frappe.dom.freeze_count) { return; } // anything open?
			frappe.dom.freeze_count--;
			if(!frappe.dom.freeze_count) {
				var freeze = $('#freeze').removeClass("in").remove();
			}
		},
		save_selection: function() {
			// via http://stackoverflow.com/questions/5605401/insert-link-in-contenteditable-element
			if (window.getSelection) {
				var sel = window.getSelection();
				if (sel.getRangeAt && sel.rangeCount) {
					var ranges = [];
					for (var i = 0, len = sel.rangeCount; i < len; ++i) {
						ranges.push(sel.getRangeAt(i));
					}
					return ranges;
				}
			} else if (document.selection && document.selection.createRange) {
				return document.selection.createRange();
			}
			return null;
		},
		restore_selection: function(savedSel) {
			if (savedSel) {
				if (window.getSelection) {
					var sel = window.getSelection();
					sel.removeAllRanges();
					for (var i = 0, len = savedSel.length; i < len; ++i) {
						sel.addRange(savedSel[i]);
					}
				} else if (document.selection && savedSel.select) {
					savedSel.select();
				}
			}
		},
		is_touchscreen: function() {
			return ('ontouchstart' in window)
		},
		handle_broken_images: function handle_broken_images(container) {
			$(container).find('img').on('error', function (e) {
				var $img = $(e.currentTarget);
				$img.addClass('no-image');
			});
		},
		scroll_to_bottom: function scroll_to_bottom(container) {
			var $container = $(container);
			$container.scrollTop($container[0].scrollHeight);
		},
		file_to_base64: function file_to_base64(file_obj) {
			return new Promise(function (resolve) {
				var reader = new FileReader();
				reader.onload = function() {
					resolve(reader.result);
				};
				reader.readAsDataURL(file_obj);
			});
		},
		scroll_to_section: function scroll_to_section(section_name) {
			setTimeout(function () {
				var section = $(("a:contains(\"" + section_name + "\")"));
				if (section.length) {
					if(section.parent().hasClass('collapsed')) {
						// opens the section
						section.click();
					}
					frappe.ui.scroll(section.parent().parent());
				}
			}, 200);
		},
		pixel_to_inches: function pixel_to_inches(pixels) {
			var div = $('<div id="dpi" style="height: 1in; width: 1in; left: 100%; position: fixed; top: 100%;"></div>');
			div.appendTo(document.body);

			var dpi_x = document.getElementById('dpi').offsetWidth;
			var inches = pixels / dpi_x;
			div.remove();

			return inches;
		}
	};

	frappe.ellipsis = function(text, max) {
		if(!max) { max = 20; }
		text = cstr(text);
		if(text.length > max) {
			text = text.substr(0, max) + '...';
		}
		return text;
	};

	frappe.run_serially = function(tasks) {
		var result = Promise.resolve();
		tasks.forEach(function (task) {
			if(task) {
				result = result.then ? result.then(task) : Promise.resolve();
			}
		});
		return result;
	};

	frappe.load_image = function (src, onload, onerror, preprocess) {
		if ( preprocess === void 0 ) preprocess = function () {};

		var tester = new Image();
		tester.onload = function() {
			onload(this);
		};
		tester.onerror = onerror;

		preprocess(tester);
		tester.src = src;
	};

	frappe.timeout = function (seconds) {
		return new Promise(function (resolve) {
			setTimeout(function () { return resolve(); }, seconds * 1000);
		});
	};

	frappe.scrub = function(text, spacer) {
		if ( spacer === void 0 ) spacer='_';

		return text.replace(/ /g, spacer).toLowerCase();
	};

	frappe.get_modal = function(title, content) {
		return $(("<div class=\"modal fade\" style=\"overflow: auto;\" tabindex=\"-1\">\n\t\t<div class=\"modal-dialog\">\n\t\t\t<div class=\"modal-content\">\n\t\t\t\t<div class=\"modal-header\">\n\t\t\t\t\t<div class=\"flex justify-between\">\n\t\t\t\t\t\t<div class=\"fill-width\">\n\t\t\t\t\t\t\t<span class=\"indicator hidden\"></span>\n\t\t\t\t\t\t\t<h4 class=\"modal-title\" style=\"font-weight: bold;\">" + title + "</h4>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div>\n\t\t\t\t\t\t\t<div class=\"text-right buttons\">\n\t\t\t\t\t\t\t\t<button type=\"button\" class=\"btn btn-default btn-sm btn-modal-minimize hide\">\n\t\t\t\t\t\t\t\t\t<i class=\"octicon octicon-chevron-down\" style=\"padding: 1px 0px;\"></i>\n\t\t\t\t\t\t\t\t</button>\n\t\t\t\t\t\t\t\t<button type=\"button\" class=\"btn btn-default btn-sm btn-modal-close\" data-dismiss=\"modal\">\n\t\t\t\t\t\t\t\t\t<i class=\"octicon octicon-x visible-xs\" style=\"padding: 1px 0px;\"></i>\n\t\t\t\t\t\t\t\t\t<span class=\"hidden-xs\">" + (__("Close")) + "</span>\n\t\t\t\t\t\t\t\t</button>\n\t\t\t\t\t\t\t\t<button type=\"button\" class=\"btn btn-primary btn-sm hide\">\n\t\t\t\t\t\t\t\t\t" + (__("Confirm")) + "\n\t\t\t\t\t\t\t\t</button>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t\t<div class=\"modal-body ui-front\">" + content + "</div>\n\t\t\t</div>\n\t\t</div>\n\t</div>"));
	};

	frappe.is_online = function() {
		if (frappe.boot.developer_mode == 1) {
			// always online in developer_mode
			return true;
		}
		if ('onLine' in navigator) {
			return navigator.onLine;
		}
		return true;
	};

	// bind online/offline events
	$(window).on('online', function() {
		frappe.show_alert({
			indicator: 'green',
			message: __('You are connected to internet.')
		});
	});

	$(window).on('offline', function() {
		frappe.show_alert({
			indicator: 'orange',
			message: __('Connection lost. Some features might not work.')
		});
	});

	frappe.provide('frappe.ui');

	window.cur_dialog = null;

	frappe.ui.open_dialogs = [];

	frappe.ui.Dialog = class Dialog extends frappe.ui.FieldGroup {
		constructor(opts) {
			super();
			this.display = false;
			this.is_dialog = true;

			$.extend(this, { animate: true, size: null }, opts);
			this.make();
		}

		make() {
			var this$1 = this;

			this.$wrapper = frappe.get_modal("", "");

			if(this.static) {
				this.$wrapper.modal({
					backdrop: 'static',
					keyboard: false
				});
				this.get_close_btn().hide();
			}

			this.wrapper = this.$wrapper.find('.modal-dialog')
				.get(0);
			if ( this.size == "small" )
				{ $(this.wrapper).addClass("modal-sm"); }
			else if ( this.size == "large" )
				{ $(this.wrapper).addClass("modal-lg"); }

			this.make_head();
			this.modal_body = this.$wrapper.find(".modal-body");
			this.$body = $('<div></div>').appendTo(this.modal_body);
			this.body = this.$body.get(0);
			this.$message = $('<div class="hide modal-message"></div>').appendTo(this.modal_body);
			this.header = this.$wrapper.find(".modal-header");

			// make fields (if any)
			super.make();

			// show footer
			this.action = this.action || { primary: { }, secondary: { } };
			if(this.primary_action || (this.action.primary && this.action.primary.onsubmit)) {
				this.set_primary_action(this.primary_action_label || this.action.primary.label || __("Submit"),
					this.primary_action || this.action.primary.onsubmit);
			}

			if(this.secondary_action) {
				this.set_secondary_action(this.secondary_action);
			}

			if (this.secondary_action_label || (this.action.secondary && this.action.secondary.label)) {
				this.get_close_btn().html(this.secondary_action_label || this.action.secondary.label);
			}

			if (this.minimizable) {
				this.header.find('.modal-title').click(function () { return this$1.toggle_minimize(); });
				this.get_minimize_btn().removeClass('hide').on('click', function () { return this$1.toggle_minimize(); });
			}

			var me = this;
			this.$wrapper
				.on("hide.bs.modal", function() {
					me.display = false;
					me.secondary_action && me.secondary_action();

					if(frappe.ui.open_dialogs[frappe.ui.open_dialogs.length-1]===me) {
						frappe.ui.open_dialogs.pop();
						if(frappe.ui.open_dialogs.length) {
							window.cur_dialog = frappe.ui.open_dialogs[frappe.ui.open_dialogs.length-1];
						} else {
							window.cur_dialog = null;
						}
					}
					me.onhide && me.onhide();
					me.on_hide && me.on_hide();
				})
				.on("shown.bs.modal", function() {
					// focus on first input
					me.display = true;
					window.cur_dialog = me;
					frappe.ui.open_dialogs.push(me);
					me.focus_on_first_input();
					me.on_page_show && me.on_page_show();
					$(document).trigger('frappe.ui.Dialog:shown');
				})
				.on('scroll', function() {
					var $input = $('input:focus');
					if($input.length && ['Date', 'Datetime',
						'Time'].includes($input.attr('data-fieldtype'))) {
						$input.blur();
					}
				});

		}

		get_primary_btn() {
			return this.$wrapper.find(".modal-header .btn-primary");
		}

		get_minimize_btn() {
			return this.$wrapper.find(".modal-header .btn-modal-minimize");
		}

		set_message(text) {
			this.$message.removeClass('hide');
			this.$body.addClass('hide');
			this.$message.text(text);
		}

		clear_message() {
			this.$message.addClass('hide');
			this.$body.removeClass('hide');
		}

		clear() {
			super.clear();
			this.clear_message();
		}

		set_primary_action(label, click) {
			this.has_primary_action = true;
			var me = this;
			return this.get_primary_btn()
				.removeClass("hide")
				.html(label)
				.click(function() {
					me.primary_action_fulfilled = true;
					// get values and send it
					// as first parameter to click callback
					// if no values then return
					var values = me.get_values();
					if(!values) { return; }
					click && click.apply(me, [values]);
				});
		}

		set_secondary_action(click) {
			this.get_close_btn().on('click', click);
		}

		disable_primary_action() {
			this.get_primary_btn().addClass('disabled');
		}
		enable_primary_action() {
			this.get_primary_btn().removeClass('disabled');
		}
		make_head() {
			this.set_title(this.title);
		}
		set_title(t) {
			this.$wrapper.find(".modal-title").html(t);
		}
		show() {
			// show it
			if ( this.animate ) {
				this.$wrapper.addClass('fade');
			} else {
				this.$wrapper.removeClass('fade');
			}
			this.$wrapper.modal("show");

			// clear any message
			this.clear_message();

			this.primary_action_fulfilled = false;
			this.is_visible = true;
			return this;
		}
		hide() {
			this.$wrapper.modal("hide");
			this.is_visible = false;
		}
		get_close_btn() {
			return this.$wrapper.find(".btn-modal-close");
		}
		no_cancel() {
			this.get_close_btn().toggle(false);
		}
		cancel() {
			this.get_close_btn().trigger("click");
		}
		toggle_minimize() {
			var modal = this.$wrapper.closest('.modal').toggleClass('modal-minimize');
			modal.attr('tabindex') ? modal.removeAttr('tabindex') : modal.attr('tabindex', -1);
			this.get_minimize_btn().find('i').toggleClass('octicon-chevron-down').toggleClass('octicon-chevron-up');
			this.is_minimized = !this.is_minimized;
			this.on_minimize_toggle && this.on_minimize_toggle(this.is_minimized);
			this.header.find('.modal-title').toggleClass('cursor-pointer');
		}
	};

	// frappe.ui.Capture
	// Author - Achilles Rasquinha <achilles@frappe.io>

	/**
	 * @description Converts a canvas, image or a video to a data URL string.
	 * 
	 * @param 	{HTMLElement} element - canvas, img or video.
	 * @returns {string} 			  - The data URL string.
	 * 
	 * @example
	 * frappe._.get_data_uri(video)
	 * // returns "data:image/pngbase64,..."
	 */
	frappe._.get_data_uri = function (element) {
		var $element = $(element);
		var width    = $element.width();
		var height   = $element.height();

		var $canvas     = $('<canvas/>');
		$canvas[0].width  = width;
		$canvas[0].height = height;

		var context     = $canvas[0].getContext('2d');
		context.drawImage($element[0], 0, 0, width, height);
		
		var data_uri = $canvas[0].toDataURL('image/png');

		return data_uri
	};

	/**
	 * @description Frappe's Capture object.
	 * 
	 * @example
	 * const capture = frappe.ui.Capture()
	 * capture.show()
	 * 
	 * capture.click((data_uri) => {
	 * 	// do stuff
	 * })
	 * 
	 * @see https://developer.mozilla.org/en-US/docs/Web/API/WebRTC_API/Taking_still_photos
	 */
	frappe.ui.Capture = class
	{
		constructor (options)
		{
		if ( options === void 0 ) options = { };

			this.options = frappe.ui.Capture.OPTIONS;
			this.set_options(options);
		}
		
		set_options (options)
		{
			this.options = Object.assign({}, frappe.ui.Capture.OPTIONS, options);
			
			return this
		}
		
		render ( )
		{
			var this$1 = this;

			return navigator.mediaDevices.getUserMedia({ video: true }).then(function (stream) {
				this$1.dialog 	 = new frappe.ui.Dialog({
					  title: this$1.options.title,
					animate: this$1.options.animate,
					 action:
					{
						secondary:
						{
							label: "<b>&times</b>"
						}
					}
				});
		
				var $e 		 = $(frappe.ui.Capture.TEMPLATE);
				
				var video      = $e.find('video')[0];
				video.srcObject  = stream;
				video.play();
				
				var $container = $(this$1.dialog.body);
				$container.html($e);
				
				$e.find('.fc-btf').hide();

				$e.find('.fc-bcp').click(function () {
					var data_url = frappe._.get_data_uri(video);
					$e.find('.fc-p').attr('src', data_url);

					$e.find('.fc-s').hide();
					$e.find('.fc-p').show();

					$e.find('.fc-btu').hide();
					$e.find('.fc-btf').show();
				});

				$e.find('.fc-br').click(function () {
					$e.find('.fc-p').hide();
					$e.find('.fc-s').show();

					$e.find('.fc-btf').hide();
					$e.find('.fc-btu').show();
				});

				$e.find('.fc-bs').click(function () {
					var data_url = frappe._.get_data_uri(video);
					this$1.hide();
					
					if (this$1.callback)
						{ this$1.callback(data_url); }
				});
			})
		}

		show ( )
		{
			var this$1 = this;

			this.render().then(function () {
				this$1.dialog.show();
			}).catch(function (err) {
				if ( this$1.options.error )
				{
					var alert = "<span class=\"indicator red\"/> " + (frappe.ui.Capture.ERR_MESSAGE);
					frappe.show_alert(alert, 3);
				}

				throw err
			});
		}

		hide ( )
		{
			if ( this.dialog )
				{ this.dialog.hide(); }
		}

		submit (fn)
		{
			this.callback = fn;
		}
	};
	frappe.ui.Capture.OPTIONS =
	{
		  title: __("Camera"),
		animate: false,
		  error: false,
	};
	frappe.ui.Capture.ERR_MESSAGE = __("Unable to load camera.");
	frappe.ui.Capture.TEMPLATE 	  =
	"\n<div class=\"frappe-capture\">\n\t<div class=\"panel panel-default\">\n\t\t<img class=\"fc-p img-responsive\"/>\n\t\t<div class=\"fc-s  embed-responsive embed-responsive-16by9\">\n\t\t\t<video class=\"embed-responsive-item\">" + (frappe.ui.Capture.ERR_MESSAGE) + "</video>\n\t\t</div>\n\t</div>\n\t<div>\n\t\t<div class=\"fc-btf\">\n\t\t\t<div class=\"row\">\n\t\t\t\t<div class=\"col-md-6\">\n\t\t\t\t\t<div class=\"pull-left\">\n\t\t\t\t\t\t<button class=\"btn btn-default fc-br\">\n\t\t\t\t\t\t\t<small>" + (__('Retake')) + "</small>\n\t\t\t\t\t\t</button>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t\t<div class=\"col-md-6\">\n\t\t\t\t\t<div class=\"pull-right\">\n\t\t\t\t\t\t<button class=\"btn btn-primary fc-bs\">\n\t\t\t\t\t\t\t<small>" + (__('Submit')) + "</small>\n\t\t\t\t\t\t</button>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t\t<div class=\"fc-btu\">\n\t\t\t<div class=\"row\">\n\t\t\t\t<div class=\"col-md-6\">\n\t\t\t\t\t" + ('') + "\n\t\t\t\t</div>\n\t\t\t\t<div class=\"col-md-6\">\n\t\t\t\t\t<div class=\"pull-right\">\n\t\t\t\t\t\t<button class=\"btn btn-default fc-bcp\">\n\t\t\t\t\t\t\t<small>" + (__('Take Photo')) + "</small>\n\t\t\t\t\t\t</button>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n</div>\n";

	frappe.user_info = function(uid) {
		if(!uid)
			{ uid = frappe.session.user; }

		if(uid.toLowerCase()==="bot") {
			return {
				fullname: __("Bot"),
				image: "/assets/frappe/images/ui/bot.png",
				abbr: "B"
			};
		}

		if(!(frappe.boot.user_info && frappe.boot.user_info[uid])) {
			var user_info = {
				fullname: frappe.utils.capitalize(uid.split("@")[0]) || "Unknown"
			};
		} else {
			var user_info = frappe.boot.user_info[uid];
		}

		user_info.abbr = frappe.get_abbr(user_info.fullname);
		user_info.color = frappe.get_palette(user_info.fullname);

		return user_info;
	};

	frappe.ui.set_user_background = function(src, selector, style) {
		if(!selector) { selector = "#page-desktop"; }
		if(!style) { style = "Fill Screen"; }
		if(src) {
			if (window.cordova && src.indexOf("http") === -1) {
				src = frappe.base_url + src;
			}
			var background = repl('background: url("%(src)s") center center;', {src: src});
		} else {
			var background = "background-color: #4B4C9D;";
		}

		frappe.dom.set_style(repl('%(selector)s { \
		%(background)s \
		background-attachment: fixed; \
		%(style)s \
	}', {
			selector:selector,
			background:background,
			style: style==="Fill Screen" ? "background-size: cover;" : ""
		}));
	};

	frappe.provide('frappe.user');

	$.extend(frappe.user, {
		name: 'Guest',
		full_name: function(uid) {
			return uid === frappe.session.user ?
				__("You") :
				frappe.user_info(uid).fullname;
		},
		image: function(uid) {
			return frappe.user_info(uid).image;
		},
		abbr: function(uid) {
			return frappe.user_info(uid).abbr;
		},
		has_role: function(rl) {
			if(typeof rl=='string')
				{ rl = [rl]; }
			for(var i in rl) {
				if((frappe.boot ? frappe.boot.user.roles : ['Guest']).indexOf(rl[i])!=-1)
					{ return true; }
			}
		},
		get_desktop_items: function() {
			// hide based on permission
			var modules_list = $.map(frappe.boot.allowed_modules, function(icon) {
				var m = icon.module_name;
				var type = frappe.modules[m] && frappe.modules[m].type;

				if(frappe.boot.user.allow_modules.indexOf(m) === -1) { return null; }

				var ret = null;
				if (type === "module") {
					if(frappe.boot.user.allow_modules.indexOf(m)!=-1 || frappe.modules[m].is_help)
						{ ret = m; }
				} else if (type === "page") {
					if(frappe.boot.allowed_pages.indexOf(frappe.modules[m].link)!=-1)
						{ ret = m; }
				} else if (type === "list") {
					if(frappe.model.can_read(frappe.modules[m]._doctype))
						{ ret = m; }
				} else if (type === "view") {
					ret = m;
				} else if (type === "setup") {
					if(frappe.user.has_role("System Manager") || frappe.user.has_role("Administrator"))
						{ ret = m; }
				} else {
					ret = m;
				}

				return ret;
			});

			return modules_list;
		},

		is_report_manager: function() {
			return frappe.user.has_role(['Administrator', 'System Manager', 'Report Manager']);
		},

		get_formatted_email: function(email) {
			var fullname = frappe.user.full_name(email);

			if (!fullname) {
				return email;
			} else {
				// to quote or to not
				var quote = '';

				// only if these special characters are found
				// why? To make the output same as that in python!
				if (fullname.search(/[\[\]\\()<>@,:;".]/) !== -1) {
					quote = '"';
				}

				return repl('%(quote)s%(fullname)s%(quote)s <%(email)s>', {
					fullname: fullname,
					email: email,
					quote: quote
				});
			}
		},

		get_emails: function ( ) {
			return Object.keys(frappe.boot.user_info).map(function (key) { return frappe.boot.user_info[key].email; });
		},

		/* Normally frappe.user is an object
		 * having properties and methods.
		 * But in the following case
		 *
		 * if (frappe.user === 'Administrator')
		 *
		 * frappe.user will cast to a string
		 * returning frappe.user.name
		 */
		toString: function() {
			return this.name;
		}
	});

	frappe.session_alive = true;
	$(document).bind('mousemove', function() {
		if(frappe.session_alive===false) {
			$(document).trigger("session_alive");
		}
		frappe.session_alive = true;
		if(frappe.session_alive_timeout)
			{ clearTimeout(frappe.session_alive_timeout); }
		frappe.session_alive_timeout = setTimeout('frappe.session_alive=false;', 30000);
	});

	// Frappe Chat

	/* eslint semi: "never" */
	// Fuck semicolons - https://mislav.net/2010/05/semicolons

	// frappe extensions

	/**
	 * @description The base class for all Frappe Errors.
	 *
	 * @example
	 * try
	 *      throw new frappe.Error("foobar")
	 * catch (e)
	 *      console.log(e.name)
	 * // returns "FrappeError"
	 *
	 * @see  https://stackoverflow.com/a/32749533
	 * @todo Requires "transform-builtin-extend" for Babel 6
	 */
	frappe.Error = Error;
	// class extends Error {
	// 	constructor (message) {
	// 		super (message)

	// 		this.name = 'FrappeError'

	// 		if ( typeof Error.captureStackTrace === 'function' )
	// 			Error.captureStackTrace(this, this.constructor)
	// 		else
	// 			this.stack = (new Error(message)).stack
	// 	}
	// }

	/**
	 * @description TypeError
	 */
	frappe.TypeError  = TypeError;
	// class extends frappe.Error {
	// 	constructor (message) {
	// 		super (message)

	// 		this.name = this.constructor.name
	// 	}
	// }

	/**
	 * @description ValueError
	 */
	frappe.ValueError = Error;
	// class extends frappe.Error {
	// 	constructor (message) {
	// 		super (message)

	// 		this.name = this.constructor.name
	// 	}
	// }

	/**
	 * @description ImportError
	 */
	frappe.ImportError = Error;
	// class extends frappe.Error {
	// 	constructor (message) {
	// 		super (message)

	// 		this.name  = this.constructor.name
	// 	}
	// }

	// frappe.datetime
	frappe.provide('frappe.datetime');

	/**
	 * @description Frappe's datetime object. (Inspired by Python's datetime object).
	 *
	 * @example
	 * const datetime = new frappe.datetime.datetime()
	 */
	frappe.datetime.datetime = class {
		/**
		 * @description Frappe's datetime Class's constructor.
		 */
		constructor (instance, format) {
		if ( format === void 0 ) format = null;

			if ( typeof moment === 'undefined' )
				{ throw new frappe.ImportError("Moment.js not installed.") }

			this.moment = instance ? moment(instance, format) : moment();
		}

		/**
		 * @description Returns a formatted string of the datetime object.
		 */
		format (format) {
			if ( format === void 0 ) format = null;

			var  formatted = this.moment.format(format);
			return formatted
		}
	};

	/**
	 * @description Frappe's daterange object.
	 *
	 * @example
	 * const range = new frappe.datetime.range(frappe.datetime.now(), frappe.datetime.now())
	 * range.contains(frappe.datetime.now())
	 */
	frappe.datetime.range   = class {
		constructor (start, end) {
			if ( typeof moment === undefined )
				{ throw new frappe.ImportError("Moment.js not installed.") }

			this.start = start;
			this.end   = end;
		}

		contains (datetime) {
			var  contains = datetime.moment.isBetween(this.start.moment, this.end.moment);
			return contains
		}
	};

	/**
	 * @description Returns the current datetime.
	 *
	 * @example
	 * const datetime = new frappe.datetime.now()
	 */
	frappe.datetime.now   = function () { return new frappe.datetime.datetime(); };

	frappe.datetime.equal = function (a, b, type) {
		a = a.moment;
		b = b.moment;

		var equal = a.isSame(b, type);

		return equal
	};

	/**
	 * @description Compares two frappe.datetime.datetime objects.
	 *
	 * @param   {frappe.datetime.datetime} a - A frappe.datetime.datetime/moment object.
	 * @param   {frappe.datetime.datetime} b - A frappe.datetime.datetime/moment object.
	 *
	 * @returns {number} 0 (if a and b are equal), 1 (if a is before b), -1 (if a is after b).
	 *
	 * @example
	 * frappe.datetime.compare(frappe.datetime.now(), frappe.datetime.now())
	 * // returns 0
	 * const then = frappe.datetime.now()
	 *
	 * frappe.datetime.compare(then, frappe.datetime.now())
	 * // returns 1
	 */
	frappe.datetime.compare = function (a, b) {
		a = a.moment;
		b = b.moment;

		if ( a.isBefore(b) )
			{ return  1 }
		else
		if ( b.isBefore(a) )
			{ return -1 }
		else
			{ return  0 }
	};

	// frappe.quick_edit
	frappe.quick_edit      = function (doctype, docname, fn) {
		return new Promise(function (resolve) {
			frappe.model.with_doctype(doctype, function () {
				frappe.db.get_doc(doctype, docname).then(function (doc)  {
					var meta     = frappe.get_meta(doctype);
					var fields   = meta.fields;
					var required = fields.filter(function (f) { return f.reqd || f.bold && !f.read_only; });

					required.map(function (f) {
						if(f.fieldname == 'content' && doc.type == 'File') {
							f['read_only'] = 1;
						}
					});

					var dialog   = new frappe.ui.Dialog({
						 title: __(("Edit " + doctype + " (" + docname + ")")),
						fields: required,
						action: {
							primary: {
								   label: __("Save"),
								onsubmit: function (values) {
									frappe.call('frappe.client.save',
										{ doc: Object.assign({}, {doctype: doctype, docname: docname}, doc, values) })
										  .then(function (r) {
											if ( fn )
												{ fn(r.message); }

											resolve(r.message);
										  });

									dialog.hide();
								}
							},
							secondary: {
								label: __("Discard")
							}
						}
					});
					dialog.set_values(doc);

					var $element = $(dialog.body);
					$element.append(("\n\t\t\t\t\t<div class=\"qe-fp\" style=\"padding-top: '15px'; padding-bottom: '15px'; padding-left: '7px'\">\n\t\t\t\t\t\t<button class=\"btn btn-default btn-sm\">\n\t\t\t\t\t\t\t" + (__("Edit in Full Page")) + "\n\t\t\t\t\t\t</button>\n\t\t\t\t\t</div>\n\t\t\t\t"));
					$element.find('.qe-fp').click(function () {
						dialog.hide();
						frappe.set_route(("Form/" + doctype + "/" + docname));
					});

					dialog.show();
				});
			});
		})
	};

	// frappe._
	// frappe's utility namespace.
	frappe.provide('frappe._');

	// String Utilities

	/**
	 * @description Python-inspired format extension for string objects.
	 *
	 * @param  {string} string - A string with placeholders.
	 * @param  {object} object - An object with placeholder, value pairs.
	 *
	 * @return {string}        - The formatted string.
	 *
	 * @example
	 * frappe._.format('{foo} {bar}', { bar: 'foo', foo: 'bar' })
	 * // returns "bar foo"
	 */
	frappe._.format = function (string, object) {
		for (var key in object)
			{ string  = string.replace(("{" + key + "}"), object[key]); }

		return string
	};

	/**
	 * @description Fuzzy Search a given query within a dataset.
	 *
	 * @param  {string} query   - A query string.
	 * @param  {array}  dataset - A dataset to search within, can contain singletons or objects.
	 * @param  {object} options - Options as per fuze.js
	 *
	 * @return {array}          - The fuzzy matched index/object within the dataset.
	 *
	 * @example
	 * frappe._.fuzzy_search("foobar", ["foobar", "bartender"])
	 * // returns [0, 1]
	 *
	 * @see http://fusejs.io
	 */
	frappe._.fuzzy_search = function (query, dataset, options) {
		var DEFAULT     = {
					shouldSort: true,
					 threshold: 0.6,
					  location: 0,
					  distance: 100,
			minMatchCharLength: 1,
			  maxPatternLength: 32
		};
		options       = Object.assign({}, DEFAULT, options);

		var fuse    = new Fuse(dataset, options);
		var result  = fuse.search(query);

		return result
	};

	/**
	 * @description Pluralizes a given word.
	 *
	 * @param  {string} word  - The word to be pluralized.
	 * @param  {number} count - The count.
	 *
	 * @return {string}       - The pluralized string.
	 *
	 * @example
	 * frappe._.pluralize('member',  1)
	 * // returns "member"
	 * frappe._.pluralize('members', 0)
	 * // returns "members"
	 *
	 * @todo Handle more edge cases.
	 */
	frappe._.pluralize = function (word, count, suffix) {
		if ( count === void 0 ) count = 0;
		if ( suffix === void 0 ) suffix = 's';

		return ("" + word + (count === 1 ? '' : suffix));
	};

	/**
	 * @description Captializes a given string.
	 *
	 * @param   {word}  - The word to be capitalized.
	 *
	 * @return {string} - The capitalized word.
	 *
	 * @example
	 * frappe._.capitalize('foobar')
	 * // returns "Foobar"
	 */
	frappe._.capitalize = function (word) { return ("" + (word.charAt(0).toUpperCase()) + (word.slice(1))); };

	// Array Utilities

	/**
	 * @description Returns the first element of an array.
	 *
	 * @param   {array} array - The array.
	 *
	 * @returns - The first element of an array, undefined elsewise.
	 *
	 * @example
	 * frappe._.head([1, 2, 3])
	 * // returns 1
	 * frappe._.head([])
	 * // returns undefined
	 */
	frappe._.head = function (arr) { return frappe._.is_empty(arr) ? undefined : arr[0]; };

	/**
	 * @description Returns a copy of the given array (shallow).
	 *
	 * @param   {array} array - The array to be copied.
	 *
	 * @returns {array}       - The copied array.
	 *
	 * @example
	 * frappe._.copy_array(["foobar", "barfoo"])
	 * // returns ["foobar", "barfoo"]
	 *
	 * @todo Add optional deep copy.
	 */
	frappe._.copy_array = function (array) {
		if ( Array.isArray(array) )
			{ return array.slice() }
		else
			{ throw frappe.TypeError(("Expected Array, recieved " + (typeof array) + " instead.")) }
	};

	/**
	 * @description Check whether an array|string|object|jQuery is empty.
	 *
	 * @param   {any}     value - The value to be checked on.
	 *
	 * @returns {boolean}       - Returns if the object is empty.
	 *
	 * @example
	 * frappe._.is_empty([])      // returns true
	 * frappe._.is_empty(["foo"]) // returns false
	 *
	 * frappe._.is_empty("")      // returns true
	 * frappe._.is_empty("foo")   // returns false
	 *
	 * frappe._.is_empty({ })            // returns true
	 * frappe._.is_empty({ foo: "bar" }) // returns false
	 *
	 * frappe._.is_empty($('.papito'))   // returns false
	 *
	 * @todo Handle other cases.
	 */
	frappe._.is_empty = function (value) {
		var empty = false;

		if ( value === undefined || value === null )
			{ empty = true; }
		else
		if ( Array.isArray(value) || typeof value === 'string' || value instanceof $ )
			{ empty = value.length === 0; }
		else
		if ( typeof value === 'object' )
			{ empty = Object.keys(value).length === 0; }

		return empty
	};

	/**
	 * @description Converts a singleton to an array, if required.
	 *
	 * @param {object} item - An object
	 *
	 * @example
	 * frappe._.as_array("foo")
	 * // returns ["foo"]
	 *
	 * frappe._.as_array(["foo"])
	 * // returns ["foo"]
	 *
	 * @see https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList-T...-
	 */
	frappe._.as_array = function (item) { return Array.isArray(item) ? item : [item]; };

	/**
	 * @description Return a singleton if array contains a single element.
	 *
	 * @param   {array}        list - An array to squash.
	 *
	 * @returns {array|object}      - Returns an array if there's more than 1 object else the first object itself.
	 *
	 * @example
	 * frappe._.squash(["foo"])
	 * // returns "foo"
	 *
	 * frappe._.squash(["foo", "bar"])
	 * // returns ["foo", "bar"]
	 */
	frappe._.squash = function (list) { return Array.isArray(list) && list.length === 1 ? list[0] : list; };

	/**
	 * @description Returns true, if the current device is a mobile device.
	 *
	 * @example
	 * frappe._.is_mobile()
	 * // returns true|false
	 *
	 * @see https://developer.mozilla.org/en-US/docs/Web/HTTP/Browser_detection_using_the_user_agent
	 */
	frappe._.is_mobile = function () {
		var regex    = new RegExp("Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini", "i");
		var agent    = navigator.userAgent;
		var mobile   = regex.test(agent);

		return mobile
	};

	/**
	 * @description Removes falsey values from an array.
	 *
	 * @example
	 * frappe._.compact([1, 2, false, NaN, ''])
	 * // returns [1, 2]
	 */
	frappe._.compact   = function (array) { return array.filter(Boolean); };

	// extend utils to base.
	frappe.utils       = Object.assign({}, frappe.utils, frappe._);

	// frappe extensions

	// frappe.user extensions
	/**
	 * @description Returns the first name of a User.
	 *
	 * @param {string} user - User
	 *
	 * @returns The first name of the user.
	 *
	 * @example
	 * frappe.user.first_name("Rahul Malhotra")
	 * // returns "Rahul"
	 */
	frappe.provide('frappe.user');
	frappe.user.first_name = function (user) { return frappe._.head(frappe.user.full_name(user).split(" ")); };

	frappe.provide('frappe.ui.keycode');
	frappe.ui.keycode = { RETURN: 13 };

	/**
	 * @description Frappe's Store Class
	 */
	 // frappe.stores  - A registry for frappe stores.
	frappe.provide('frappe.stores');
	frappe.stores = [ ];
	frappe.Store  = class
	{
		/**
		 * @description Frappe's Store Class's constructor.
		 *
		 * @param {string} name - Name of the logger.
		 */
		constructor (name) {
			if ( typeof name !== 'string' )
				{ throw new frappe.TypeError(("Expected string for name, got " + (typeof name) + " instead.")) }
			this.name = name;
		}

		/**
		 * @description Get instance of frappe.Store (return registered one if declared).
		 *
		 * @param {string} name - Name of the store.
		 */
		static get (name) {
			if ( !(name in frappe.stores) )
				{ frappe.stores[name] = new frappe.Store(name); }
			return frappe.stores[name]
		}

		set (key, value) { localStorage.setItem(((this.name) + ":" + key), value); }
		get (key, value) { return localStorage.getItem(((this.name) + ":" + key)) }
	};

	// frappe.loggers - A registry for frappe loggers.
	frappe.provide('frappe.loggers');
	/**
	 * @description Frappe's Logger Class
	 *
	 * @example
	 * frappe.log       = frappe.Logger.get('foobar')
	 * frappe.log.level = frappe.Logger.DEBUG
	 *
	 * frappe.log.info('foobar')
	 * // prints '[timestamp] foobar: foobar'
	 */
	frappe.Logger = class {
		/**
		 * @description Frappe's Logger Class's constructor.
		 *
		 * @param {string} name - Name of the logger.
		 */
		constructor (name, level) {
			if ( typeof name !== 'string' )
				{ throw new frappe.TypeError(("Expected string for name, got " + (typeof name) + " instead.")) }

			this.name   = name;
			this.level  = level;

			if ( !this.level ) {
				if ( frappe.boot.developer_mode )
					{ this.level = frappe.Logger.ERROR; }
				else
					{ this.level = frappe.Logger.NOTSET; }
			}
			this.format = frappe.Logger.FORMAT;
		}

		/**
		 * @description Get instance of frappe.Logger (return registered one if declared).
		 *
		 * @param {string} name - Name of the logger.
		 */
		static get (name, level) {
			if ( !(name in frappe.loggers) )
				{ frappe.loggers[name] = new frappe.Logger(name, level); }
			return frappe.loggers[name]
		}

		debug (message) { this.log(message, frappe.Logger.DEBUG); }
		info  (message) { this.log(message, frappe.Logger.INFO);  }
		warn  (message) { this.log(message, frappe.Logger.WARN);  }
		error (message) { this.log(message, frappe.Logger.ERROR); }

		log (message, level) {
			var timestamp   = frappe.datetime.now();

			if ( level.value <= this.level.value ) {
				var format  = frappe._.format(this.format, {
					time: timestamp.format('HH:mm:ss'),
					name: this.name
				});
				console.log(("%c " + format + ":"), ("color: " + (level.color)), message);
			}
		}
	};

	frappe.Logger.DEBUG  = { value: 10, color: '#616161', name: 'DEBUG'  };
	frappe.Logger.INFO   = { value: 20, color: '#2196F3', name: 'INFO'   };
	frappe.Logger.WARN   = { value: 30, color: '#FFC107', name: 'WARN'   };
	frappe.Logger.ERROR  = { value: 40, color: '#F44336', name: 'ERROR'  };
	frappe.Logger.NOTSET = { value:  0,                   name: 'NOTSET' };

	frappe.Logger.FORMAT = '{time} {name}';

	// frappe.chat
	frappe.provide('frappe.chat');

	frappe.log = frappe.Logger.get('frappe.chat', frappe.Logger.NOTSET);

	// frappe.chat.profile
	frappe.provide('frappe.chat.profile');

	/**
	 * @description Create a Chat Profile.
	 *
	 * @param   {string|array} fields - (Optional) fields to be retrieved after creating a Chat Profile.
	 * @param   {function}     fn     - (Optional) callback with the returned Chat Profile.
	 *
	 * @returns {Promise}
	 *
	 * @example
	 * frappe.chat.profile.create(console.log)
	 *
	 * frappe.chat.profile.create("status").then(console.log) // { status: "Online" }
	 */
	frappe.chat.profile.create = function (fields, fn) {
		if ( typeof fields === "function" ) {
			fn     = fields;
			fields = null;
		} else
		if ( typeof fields === "string" )
			{ fields = frappe._.as_array(fields); }

		return new Promise(function (resolve) {
			frappe.call("frappe.chat.doctype.chat_profile.chat_profile.create",
				{ user: frappe.session.user, exists_ok: true, fields: fields },
					function (response) {
						if ( fn )
							{ fn(response.message); }

						resolve(response.message);
					});
		})
	};

	/**
	 * @description Updates a Chat Profile.
	 *
	 * @param   {string} user   - (Optional) Chat Profile User, defaults to session user.
	 * @param   {object} update - (Required) Updates to be dispatched.
	 *
	 * @example
	 * frappe.chat.profile.update(frappe.session.user, { "status": "Offline" })
	 */
	frappe.chat.profile.update = function (user, update, fn) {
		return new Promise(function (resolve) {
			frappe.call("frappe.chat.doctype.chat_profile.chat_profile.update",
				{ user: user || frappe.session.user, data: update },
					function (response) {
						if ( fn )
							{ fn(response.message); }

						resolve(response.message);
					});
		})
	};

	// frappe.chat.profile.on
	frappe.provide('frappe.chat.profile.on');

	/**
	 * @description Triggers on a Chat Profile update of a user (Only if there's a one-on-one conversation).
	 *
	 * @param   {function} fn - (Optional) callback with the User and the Chat Profile update.
	 *
	 * @returns {Promise}
	 *
	 * @example
	 * frappe.chat.profile.on.update(function (user, update)
	 * {
	 *      // do stuff
	 * })
	 */
	frappe.chat.profile.on.update = function (fn) {
		frappe.realtime.on("frappe.chat.profile:update", function (r) { return fn(r.user, r.data); });
	};
	frappe.chat.profile.STATUSES
	=
	[
		{
			name: "Online",
		   color: "green"
		},
		{
			 name: "Away",
			color: "yellow"
		},
		{
			 name: "Busy",
			color: "red"
		},
		{
			 name: "Offline",
			color: "darkgrey"
		}
	];

	// frappe.chat.room
	frappe.provide('frappe.chat.room');

	/**
	 * @description Creates a Chat Room.
	 *
	 * @param   {string}       kind  - (Required) "Direct", "Group" or "Visitor".
	 * @param   {string}       owner - (Optional) Chat Room owner (defaults to current user).
	 * @param   {string|array} users - (Required for "Direct" and "Visitor", Optional for "Group") User(s) within Chat Room.
	 * @param   {string}       name  - Chat Room name.
	 * @param   {function}     fn    - callback with created Chat Room.
	 *
	 * @returns {Promise}
	 *
	 * @example
	 * frappe.chat.room.create("Direct", frappe.session.user, "foo@bar.com", function (room) {
	 *      // do stuff
	 * })
	 * frappe.chat.room.create("Group",  frappe.session.user, ["santa@gmail.com", "banta@gmail.com"], "Santa and Banta", function (room) {
	 *      // do stuff
	 * })
	 */
	frappe.chat.room.create = function (kind, owner, users, name, fn) {
		if ( typeof name === "function" ) {
			fn   = name;
			name = null;
		}

		users    = frappe._.as_array(users);

		return new Promise(function (resolve) {
			frappe.call("frappe.chat.doctype.chat_room.chat_room.create",
				{ kind: kind, owner: owner || frappe.session.user, users: users, name: name },
				function (r) {
					var room = r.message;
					room     = Object.assign({}, room, {creation: new frappe.datetime.datetime(room.creation)});

					if ( fn )
						{ fn(room); }

					resolve(room);
				});
		})
	};

	/**
	 * @description Returns Chat Room(s).
	 *
	 * @param   {string|array} names   - (Optional) Chat Room(s) to retrieve.
	 * @param   {string|array} fields  - (Optional) fields to be retrieved for each Chat Room.
	 * @param   {function}     fn      - (Optional) callback with the returned Chat Room(s).
	 *
	 * @returns {Promise}
	 *
	 * @example
	 * frappe.chat.room.get(function (rooms) {
	 *      // do stuff
	 * })
	 * frappe.chat.room.get().then(function (rooms) {
	 *      // do stuff
	 * })
	 *
	 * frappe.chat.room.get(null, ["room_name", "avatar"], function (rooms) {
	 *      // do stuff
	 * })
	 *
	 * frappe.chat.room.get("CR00001", "room_name", function (room) {
	 *      // do stuff
	 * })
	 *
	 * frappe.chat.room.get(["CR00001", "CR00002"], ["room_name", "last_message"], function (rooms) {
	 *
	 * })
	 */
	frappe.chat.room.get = function (names, fields, fn) {
		if ( typeof names === "function" ) {
			fn     = names;
			names  = null;
			fields = null;
		}
		else
		if ( typeof names === "string" ) {
			names  = frappe._.as_array(names);

			if ( typeof fields === "function" ) {
				fn     = fields;
				fields = null;
			}
			else
			if ( typeof fields === "string" )
				{ fields = frappe._.as_array(fields); }
		}

		return new Promise(function (resolve) {
			frappe.call("frappe.chat.doctype.chat_room.chat_room.get",
				{ user: frappe.session.user, rooms: names, fields: fields },
					function (response) {
						var rooms = response.message;
						if ( rooms ) { // frappe.api BOGZ! (emtpy arrays are falsified, not good design).
							rooms = frappe._.as_array(rooms);
							rooms = rooms.map(function (room) {
								return Object.assign({}, room, {creation: new frappe.datetime.datetime(room.creation),
									last_message: room.last_message ? Object.assign({}, room.last_message,
										{creation: new frappe.datetime.datetime(room.last_message.creation)}) : null})
							});
							rooms = frappe._.squash(rooms);
						}
						else
							{ rooms = [ ]; }

						if ( fn )
							{ fn(rooms); }

						resolve(rooms);
					});
		})
	};

	/**
	 * @description Subscribe current user to said Chat Room(s).
	 *
	 * @param {string|array} rooms - Chat Room(s).
	 *
	 * @example
	 * frappe.chat.room.subscribe("CR00001")
	 */
	frappe.chat.room.subscribe = function (rooms) {
		frappe.realtime.publish("frappe.chat.room:subscribe", rooms);
	};

	/**
	 * @description Get Chat Room history.
	 *
	 * @param   {string} name - Chat Room name
	 *
	 * @returns {Promise}     - Chat Message(s)
	 *
	 * @example
	 * frappe.chat.room.history(function (messages)
	 * {
	 *      // do stuff.
	 * })
	 */
	frappe.chat.room.history = function (name, fn) {
		return new Promise(function (resolve) {
			frappe.call("frappe.chat.doctype.chat_room.chat_room.history",
				{ room: name, user: frappe.session.user },
					function (r) {
						var messages = r.message ? frappe._.as_array(r.message) : [ ]; // frappe.api BOGZ! (emtpy arrays are falsified, not good design).
						messages     = messages.map(function (m) {
							return Object.assign({}, m,
								{creation: new frappe.datetime.datetime(m.creation)})
						});

						if ( fn )
							{ fn(messages); }

						resolve(messages);
					});
		})
	};

	/**
	 * @description Searches Rooms based on a query.
	 *
	 * @param   {string} query - The query string.
	 * @param   {array}  rooms - A list of Chat Rooms.
	 *
	 * @returns {array}        - A fuzzy searched list of rooms.
	 */
	frappe.chat.room.search = function (query, rooms) {
		var dataset = rooms.map(function (r) {
			if ( r.room_name )
				{ return r.room_name }
			else
				if ( r.owner === frappe.session.user )
					{ return frappe.user.full_name(frappe._.squash(r.users)) }
				else
					{ return frappe.user.full_name(r.owner) }
		});
		var results = frappe._.fuzzy_search(query, dataset);
		rooms         = results.map(function (i) { return rooms[i]; });

		return rooms
	};

	/**
	 * @description Sort Chat Room(s) based on Last Message Timestamp or Creation Date.
	 *
	 * @param {array}   - A list of Chat Room(s)
	 * @param {compare} - (Optional) a comparision function.
	 */
	frappe.chat.room.sort = function (rooms, compare) {
		if ( compare === void 0 ) compare = null;

		compare = compare || function (a, b) {
			if ( a.last_message && b.last_message )
				{ return frappe.datetime.compare(a.last_message.creation, b.last_message.creation) }
			else
			if ( a.last_message )
				{ return frappe.datetime.compare(a.last_message.creation, b.creation) }
			else
			if ( b.last_message )
				{ return frappe.datetime.compare(a.creation, b.last_message.creation) }
			else
				{ return frappe.datetime.compare(a.creation, b.creation) }
		};
		rooms.sort(compare);

		return rooms
	};

	// frappe.chat.room.on
	frappe.provide('frappe.chat.room.on');

	/**
	 * @description Triggers on Chat Room updated.
	 *
	 * @param {function} fn - callback with the Chat Room and Update.
	 */
	frappe.chat.room.on.update = function (fn) {
		frappe.realtime.on("frappe.chat.room:update", function (r) {
			if ( r.data.last_message )
				// creation to frappe.datetime.datetime (easier to manipulate).
				{ r.data = Object.assign({}, r.data, {last_message: Object.assign({}, r.data.last_message, {creation: new frappe.datetime.datetime(r.data.last_message.creation)})}); }

			fn(r.room, r.data);
		});
	};

	/**
	 * @description Triggers on Chat Room created.
	 *
	 * @param {function} fn - callback with the created Chat Room.
	 */
	frappe.chat.room.on.create = function (fn) {
		frappe.realtime.on("frappe.chat.room:create", function (r) { return fn(Object.assign({}, r, {creation: new frappe.datetime.datetime(r.creation)})); }
		);
	};

	/**
	 * @description Triggers when a User is typing in a Chat Room.
	 *
	 * @param {function} fn - callback with the typing User within the Chat Room.
	 */
	frappe.chat.room.on.typing = function (fn) {
		frappe.realtime.on("frappe.chat.room:typing", function (r) { return fn(r.room, r.user); });
	};

	// frappe.chat.message
	frappe.provide('frappe.chat.message');

	frappe.chat.message.typing = function (room, user) {
		frappe.realtime.publish("frappe.chat.message:typing", { user: user || frappe.session.user, room: room });
	};

	frappe.chat.message.send   = function (room, message, type) {
		if ( type === void 0 ) type = "Content";

		frappe.call("frappe.chat.doctype.chat_message.chat_message.send",
			{ user: frappe.session.user, room: room, content: message, type: type });
	};

	frappe.chat.message.update = function (message, update, fn) {
		return new Promise(function (resolve) {
			frappe.call('frappe.chat.doctype.chat_message.chat_message.update',
				{ user: frappe.session.user, message: message, update: update },
				function (r) {
					if ( fn )
						{ fn(response.message); }

					resolve(response.message);
				});
		})
	};

	frappe.chat.message.sort   = function (messages) {
		if ( !frappe._.is_empty(messages) )
			{ messages.sort(function (a, b) { return frappe.datetime.compare(b.creation, a.creation); }); }

		return messages
	};

	/**
	 * @description Add user to seen (defaults to session.user)
	 */
	frappe.chat.message.seen   = function (mess, user) {
		frappe.call('frappe.chat.doctype.chat_message.chat_message.seen',
			{ message: mess, user: user || frappe.session.user });
	};

	frappe.provide('frappe.chat.message.on');
	frappe.chat.message.on.create = function (fn) {
		frappe.realtime.on("frappe.chat.message:create", function (r) { return fn(Object.assign({}, r, {creation: new frappe.datetime.datetime(r.creation)})); }
		);
	};

	frappe.chat.message.on.update = function (fn) {
		frappe.realtime.on("frappe.chat.message:update", function (r) { return fn(r.message, r.data); });
	};

	frappe.chat.pretty_datetime   = function (date) {
		var today    = moment();
		var instance = date.moment;

		if ( today.isSame(instance, "d") )
			{ return instance.format("hh:mm A") }
		else
		if ( today.isSame(instance, "week") )
			{ return instance.format("dddd") }
		else
			{ return instance.format("DD/MM/YYYY") }
	};

	// frappe.chat.sound
	frappe.provide('frappe.chat.sound');

	/**
	 * @description Plays a given registered sound.
	 *
	 * @param {value} - The name of the registered sound.
	 *
	 * @example
	 * frappe.chat.sound.play("message")
	 */
	frappe.chat.sound.play  = function (name, volume) {
		if ( volume === void 0 ) volume = 0.1;

		// frappe._.play_sound(`chat-${name}`)
		var $audio = $("<audio class=\"chat-audio\"/>");
		$audio.attr('volume', volume);

		if  ( frappe._.is_empty($audio) )
			{ $(document).append($audio); }

		if  ( !$audio.paused ) {
			frappe.log.info('Stopping sound playing.');
			$audio[0].pause();
			$audio.attr('currentTime', 0);
		}

		frappe.log.info('Playing sound.');
		$audio.attr('src', ((frappe.chat.sound.PATH) + "/chat-" + name + ".mp3"));
		$audio[0].play();
	};
	frappe.chat.sound.PATH  = '/assets/frappe/sounds';

	// frappe.chat.emoji
	frappe.chat.emojis = [ ];
	frappe.chat.emoji  = function (fn) {
		return new Promise(function (resolve) {
			if ( !frappe._.is_empty(frappe.chat.emojis) ) {
				if ( fn )
					{ fn(frappe.chat.emojis); }

				resolve(frappe.chat.emojis);
			}
			else
				{ $.get('https://cdn.rawgit.com/frappe/emoji/master/emoji', function (data) {
					frappe.chat.emojis = JSON.parse(data);

					if ( fn )
						{ fn(frappe.chat.emojis); }

					resolve(frappe.chat.emojis);
				}); }
		})
	};

	// Website Settings
	frappe.provide('frappe.chat.website.settings');
	frappe.chat.website.settings = function (fields, fn) {
		if ( typeof fields === "function" ) {
			fn     = fields;
			fields = null;
		} else
		if ( typeof fields === "string" )
			{ fields = frappe._.as_array(fields); }

		return new Promise(function (resolve) {
			frappe.call("frappe.chat.website.settings",
				{ fields: fields })
				.then(function (response) {
					var message = response.message;

					if ( message.enable_from )
						{ message   = Object.assign({}, message, {enable_from: new frappe.datetime.datetime(message.enable_from, 'HH:mm:ss')}); }
					if ( message.enable_to )
						{ message   = Object.assign({}, message, {enable_to:   new frappe.datetime.datetime(message.enable_to,   'HH:mm:ss')}); }

					if ( fn )
						{ fn(message); }

					resolve(message);
				});
		})
	};

	frappe.chat.website.token    = function (fn) {
		return new Promise(function (resolve) {
			frappe.call("frappe.chat.website.token")
				.then(function (response) {
					if ( fn )
						{ fn(response.message); }

					resolve(response.message);
				});
		})
	};

	var h = hyper_min.h;
	var Component = hyper_min.Component;

	// frappe.components
	// frappe's component namespace.
	frappe.provide('frappe.components');

	frappe.provide('frappe.chat.component');

	/**
	 * @description Button Component
	 *
	 * @prop {string}  type  - (Optional) "default", "primary", "info", "success", "warning", "danger" (defaults to "default")
	 * @prop {boolean} block - (Optional) Render a button block (defaults to false).
	 */
	frappe.components.Button
	=
	class extends Component {
		render ( ) {
			var ref = this;
			var props = ref.props;
			var size      = frappe.components.Button.SIZE[props.size];

			return (
				h("button", Object.assign({}, props, {class: ("btn " + (size && size.class) + " btn-" + (props.type) + " " + (props.block ? "btn-block" : "") + " " + (props.class ? props.class : ""))}),
					props.children
				)
			)
		}
	};
	frappe.components.Button.SIZE
	=
	{
		small: {
			class: "btn-sm"
		},
		large: {
			class: "btn-lg"
		}
	};
	frappe.components.Button.defaultProps
	=
	{
		 type: "default",
		block: false
	};

	/**
	 * @description FAB Component
	 *
	 * @extends frappe.components.Button
	 */
	frappe.components.FAB
	=
	class extends frappe.components.Button {
		render ( ) {
			var ref = this;
			var props = ref.props;
			var size      = frappe.components.FAB.SIZE[props.size];

			return (
				h(frappe.components.Button, Object.assign({}, props, {class: ((props.class) + " " + (size && size.class))}),
					h("i", { class: props.icon })
				)
			)
		}
	};
	frappe.components.FAB.defaultProps
	=
	{
		icon: "octicon octicon-plus"
	};
	frappe.components.FAB.SIZE
	=
	{
		small:
		{
			class: "frappe-fab-sm"
		},
		large:
		{
			class: "frappe-fab-lg"
		}
	};

	/**
	 * @description Octicon Component
	 *
	 * @prop color - (Required) color for the indicator
	 */
	frappe.components.Indicator
	=
	class extends Component {
		render ( ) {
			var ref = this;
			var props = ref.props;

			return props.color ? h("span", Object.assign({}, props, {class: ("indicator " + (props.color))})) : null
		}
	};

	/**
	 * @description FontAwesome Component
	 */
	frappe.components.FontAwesome
	=
	class extends Component {
		render ( ) {
			var ref = this;
			var props = ref.props;

			return props.type ? h("i", Object.assign({}, props, {class: ("fa " + (props.fixed ? "fa-fw" : "") + " fa-" + (props.type) + " " + (props.class))})) : null
		}
	};
	frappe.components.FontAwesome.defaultProps
	=
	{
		fixed: false
	};

	/**
	 * @description Octicon Component
	 *
	 * @extends frappe.Component
	 */
	frappe.components.Octicon
	=
	class extends Component {
		render ( ) {
			var ref = this;
			var props = ref.props;

			return props.type ? h("i", Object.assign({}, props, {class: ("octicon octicon-" + (props.type))})) : null
		}
	};

	/**
	 * @description Avatar Component
	 *
	 * @prop {string} title - (Optional) title for the avatar.
	 * @prop {string} abbr  - (Optional) abbreviation for the avatar, defaults to the first letter of the title.
	 * @prop {string} size  - (Optional) size of the avatar to be displayed.
	 * @prop {image}  image - (Optional) image for the avatar, defaults to the first letter of the title.
	 */
	frappe.components.Avatar
	=
	class extends Component {
		render ( ) {
			var ref = this;
			var props = ref.props;
			var abbr      = props.abbr || props.title.substr(0, 1);
			var size      = frappe.components.Avatar.SIZE[props.size] || frappe.components.Avatar.SIZE.medium;

			return (
				h("span", { class: ("avatar " + (size.class) + " " + (props.class ? props.class : "")) },
					props.image ?
						h("img", { class: "media-object", src: props.image })
						:
						h("div", { class: "standard-image" }, abbr)
				)
			)
		}
	};
	frappe.components.Avatar.SIZE
	=
	{
		small:
		{
			class: "avatar-small"
		},
		large:
		{
			class: "avatar-large"
		},
		medium:
		{
			class: "avatar-medium"
		}
	};

	/**
	 * @description Frappe Chat Object.
	 *
	 * @example
	 * const chat = new frappe.Chat(options) // appends to "body"
	 * chat.render()
	 * const chat = new frappe.Chat(".selector", options)
	 * chat.render()
	 *
	 * const chat = new frappe.Chat()
	 * chat.set_wrapper('.selector')
	 *     .set_options(options)
	 *     .render()
	 */
	frappe.Chat
	=
	class {
		/**
		 * @description Frappe Chat Object.
		 *
		 * @param {string} selector - A query selector, HTML Element or jQuery object.
		 * @param {object} options  - Optional configurations.
		 */
		constructor (selector, options) {
			if ( !(typeof selector === "string" || selector instanceof $ || selector instanceof HTMLElement) ) {
				options  = selector;
				selector = null;
			}

			this.options = frappe.Chat.OPTIONS;

			this.set_wrapper(selector ? selector : "body");
			this.set_options(options);

			// Load Emojis.
			frappe.chat.emoji();
		}

		/**
		 * Set the container on which the chat widget is mounted on.
		 * @param   {string|HTMLElement} selector - A query selector, HTML Element or jQuery object.
		 *
		 * @returns {frappe.Chat}                 - The instance.
		 *
		 * @example
		 * const chat = new frappe.Chat()
		 * chat.set_wrapper(".selector")
		 */
		set_wrapper (selector) {
			this.$wrapper = $(selector);

			return this
		}

		/**
		 * Set the configurations for the chat interface.
		 * @param   {object}      options - Optional Configurations.
		 *
		 * @returns {frappe.Chat}         - The instance.
		 *
		 * @example
		 * const chat = new frappe.Chat()
		 * chat.set_options({ layout: frappe.Chat.Layout.PAGE })
		 */
		set_options (options) {
			this.options = Object.assign({}, this.options, options);

			return this
		}

		/**
		 * @description Destory the chat widget.
		 *
		 * @returns {frappe.Chat} - The instance.
		 *
		 * @example
		 * const chat = new frappe.Chat()
		 * chat.render()
		 *     .destroy()
		 */
		destroy ( ) {
			var $wrapper = this.$wrapper;
			$wrapper.remove(".frappe-chat");

			return this
		}

		/**
		 * @description Render the chat widget component onto destined wrapper.
		 *
		 * @returns {frappe.Chat} - The instance.
		 *
		 * @example
		 * const chat = new frappe.Chat()
		 * chat.render()
		 */
		render (props) {
			if ( props === void 0 ) props = { };

			this.destroy();

			var $wrapper   = this.$wrapper;
			var options    = this.options;

			var component  = h(frappe.Chat.Widget, Object.assign({}, {layout: options.layout,
				target: options.target},
				props));

			hyper_min.render(component, $wrapper[0]);

			return this
		}
	};
	frappe.Chat.Layout
	=
	{
		PAGE: "page", POPPER: "popper"
	};
	frappe.Chat.OPTIONS
	=
	{
		layout: frappe.Chat.Layout.POPPER
	};

	/**
	 * @description The base Component for Frappe Chat
	 */
	frappe.Chat.Widget
	=
	class extends Component {
		constructor (props) {
			super (props);

			this.setup(props);
			this.make();
		}

		setup (props) {
			var this$1 = this;

			// room actions
			this.room           = { };
			this.room.add       = function (rooms) {
				rooms           = frappe._.as_array(rooms);
				var names     = rooms.map(function (r) { return r.name; });

				frappe.log.info(("Subscribing " + (frappe.session.user) + " to Chat Rooms " + (names.join(", ")) + "."));
				frappe.chat.room.subscribe(names);

				var state     = [ ];

				for (var i = 0, list = rooms; i < list.length; i += 1)
					{
					var room = list[i];

					if ( ["Group", "Visitor"].includes(room.type) || room.owner === frappe.session.user || room.last_message ) {
						frappe.log.info(("Adding " + (room.name) + " to component."));
						state.push(room);
					}
				}

				this$1.set_state({ rooms: this$1.state.rooms.concat( state ) });
			};
			this.room.update    = function (room, update) {
				var ref = this$1;
				var state = ref.state;
				var   exists    = false;
				var rooms     = state.rooms.map(function (r) {
					if ( r.name === room ) {
						exists  = true;
						if ( update.typing ) {
							if ( !frappe._.is_empty(r.typing) ) {
								var usr = update.typing;
								if ( !r.typing.includes(usr) ) {
									update.typing = frappe._.copy_array(r.typing);
									update.typing.push(usr);
								}
							}
							else
								{ update.typing = frappe._.as_array(update.typing); }
						}

						return Object.assign({}, r, update)
					}

					return r
				});

				if ( frappe.session.user !== 'Guest' ) {
					if ( !exists )
						{ frappe.chat.room.get(room, function (room) { return this$1.room.add(room); }); }
					else
						{ this$1.set_state({ rooms: rooms }); }
				}

				if ( state.room.name === room ) {
					if ( update.typing ) {
						if ( !frappe._.is_empty(state.room.typing) ) {
							var usr = update.typing;
							if ( !state.room.typing.includes(usr) ) {
								update.typing = frappe._.copy_array(state.room.typing);
								update.typing.push(usr);
							}
						} else
							{ update.typing = frappe._.as_array(update.typing); }
					}

					var room$1  = Object.assign({}, state.room, update);

					this$1.set_state({ room: room$1 });
				}
			};
			this.room.select    = function (name) {
				frappe.chat.room.history(name, function (messages) {
					var ref = this$1;
					var state = ref.state;
					var room       = state.rooms.find(function (r) { return r.name === name; });

					this$1.set_state({
						room: Object.assign({}, state.room, room, {messages: messages})
					});
				});
			};

			this.state = Object.assign({}, frappe.Chat.Widget.defaultState, props);
		}

		make ( ) {
			var this$1 = this;

			if ( frappe.session.user !== 'Guest' ) {
				frappe.chat.profile.create([
					"status", "message_preview", "notification_tones", "conversation_tones"
				]).then(function (profile) {
					this$1.set_state({ profile: profile });

					frappe.chat.room.get(function (rooms) {
						rooms = frappe._.as_array(rooms);
						frappe.log.info(("User " + (frappe.session.user) + " is subscribed to " + (rooms.length) + " " + (frappe._.pluralize('room', rooms.length)) + "."));

						if ( !frappe._.is_empty(rooms) )
							{ this$1.room.add(rooms); }
					});

					this$1.bind();
				});
			} else {
				this.bind();
			}
		}

		bind ( ) {
			var this$1 = this;

			frappe.chat.profile.on.update(function (user, update) {
				frappe.log.warn(("TRIGGER: Chat Profile update " + (JSON.stringify(update)) + " of User " + user + "."));

				if ( 'status' in update ) {
					if ( user === frappe.session.user ) {
						this$1.set_state({
							profile: Object.assign({}, this$1.state.profile, {status: update.status})
						});
					} else {
						var status = frappe.chat.profile.STATUSES.find(function (s) { return s.name === update.status; });
						var color  = status.color;

						var alert  = "<span class=\"indicator " + color + "\"/> " + (frappe.user.full_name(user)) + " is currently <b>" + (update.status) + "</b>";
						frappe.show_alert(alert, 3);
					}
				}
			});

			frappe.chat.room.on.create(function (room) {
				frappe.log.warn(("TRIGGER: Chat Room " + (room.name) + " created."));
				this$1.room.add(room);
			});

			frappe.chat.room.on.update(function (room, update) {
				frappe.log.warn(("TRIGGER: Chat Room " + room + " update " + (JSON.stringify(update)) + " recieved."));
				this$1.room.update(room, update);
			});

			frappe.chat.room.on.typing(function (room, user) {
				if ( user !== frappe.session.user ) {
					frappe.log.warn(("User " + user + " typing in Chat Room " + room + "."));
					this$1.room.update(room, { typing: user });

					setTimeout(function () { return this$1.room.update(room, { typing: null }); }, 5000);
				}
			});

			frappe.chat.message.on.create(function (r) {
				var ref = this$1;
				var state = ref.state;

				// play sound.
				if ( state.room.name )
					{ state.profile.conversation_tones && frappe.chat.sound.play('message'); }
				else
					{ state.profile.notification_tones && frappe.chat.sound.play('notification'); }

				if ( r.user !== frappe.session.user && state.profile.message_preview && !state.toggle ) {
					var $element = $('body').find('.frappe-chat-alert');
					$element.remove();

					var  alert   = // TODO: ellipses content
					"\n\t\t\t\t<span data-action=\"show-message\" class=\"cursor-pointer\">\n\t\t\t\t\t<span class=\"indicator yellow\"/> <b>" + (frappe.user.first_name(r.user)) + "</b>: " + (r.content) + "\n\t\t\t\t</span>\n\t\t\t\t";
					frappe.show_alert(alert, 3, {
						"show-message": function (r) {
							this.room.select(r.room);
							this.base.firstChild._component.toggle();
						}.bind(this$1, r)
					});
				}

				if ( r.room === state.room.name ) {
					var mess  = frappe._.copy_array(state.room.messages);
					mess.push(r);

					this$1.set_state({ room: Object.assign({}, state.room, {messages: mess}) });
				}
			});

			frappe.chat.message.on.update(function (message, update) {
				frappe.log.warn(("TRIGGER: Chat Message " + message + " update " + (JSON.stringify(update)) + " recieved."));
			});
		}

		render ( ) {
			var this$1 = this;

			var ref = this;
			var props = ref.props;
			var state = ref.state;
			var me               = this;

			var ActionBar        = h(frappe.Chat.Widget.ActionBar, {
				placeholder: __("Search or Create a New Chat"),
					  class: "level",
					 layout: props.layout,
					actions:
				frappe._.compact([
					{
						  label: __("New"),
						onclick: function ( ) {
							var dialog = new frappe.ui.Dialog({
								  title: __("New Chat"),
								 fields: [
									 {
											 label: __("Chat Type"),
										 fieldname: "type",
										 fieldtype: "Select",
										   options: ["Group", "Direct Chat"],
										   default: "Group",
										  onchange: function () {
												var type     = dialog.get_value("type");
												var is_group = type === "Group";

												dialog.set_df_property("group_name", "reqd",  is_group);
												dialog.set_df_property("user",       "reqd", !is_group);
										  }
									 },
									 {
											 label: __("Group Name"),
										 fieldname: "group_name",
										 fieldtype: "Data",
											  reqd: true,
										depends_on: "eval:doc.type == 'Group'"
									 },
									 {
											 label: __("Users"),
										 fieldname: "users",
										 fieldtype: "MultiSelect",
										   options: frappe.user.get_emails(),
										depends_on: "eval:doc.type == 'Group'"
									 },
									 {
											 label: __("User"),
										 fieldname: "user",
										 fieldtype: "Link",
										   options: "User",
										depends_on: "eval:doc.type == 'Direct Chat'"
									 }
								 ],
								action: {
									primary: {
										   label: __('Create'),
										onsubmit: function (values) {
											if ( values.type === "Group" ) {
												if ( !frappe._.is_empty(values.users) ) {
													var name  = values.group_name;
													var users = dialog.fields_dict.users.get_values();

													frappe.chat.room.create("Group",  null, users, name);
												}
											} else {
												var user      = values.user;

												frappe.chat.room.create("Direct", null, user);
											}
											dialog.hide();
										}
									}
								}
							});
							dialog.show();
						}
					},
					frappe._.is_mobile() && {
						   icon: "octicon octicon-x",
						   class: "frappe-chat-close",
						onclick: function () { return this$1.set_state({ toggle: false }); }
					}
				], Boolean),
				change: function (query) { me.set_state({ query: query }); },
				  span: function (span)  { me.set_state({ span: span  }); },
			});

			var   contacts   = [ ];
			if ( 'user_info' in frappe.boot ) {
				var emails = frappe.user.get_emails();
				for (var i$1 = 0, list$1 = emails; i$1 < list$1.length; i$1 += 1) {
					var email = list$1[i$1];

					var exists = false;

					for (var i = 0, list = state.rooms; i < list.length; i += 1) {
						var room = list[i];

						if ( room.type === 'Direct' ) {
							if ( room.owner === email || frappe._.squash(room.users) === email )
								{ exists = true; }
						}
					}

					if ( !exists )
						{ contacts.push({ owner: frappe.session.user, users: [email] }); }
				}
			}
			var rooms      = state.query ? frappe.chat.room.search(state.query, state.rooms.concat(contacts)) : frappe.chat.room.sort(state.rooms);

			var layout     = state.span  ? frappe.Chat.Layout.PAGE : frappe.Chat.Layout.POPPER;

			var RoomList   = frappe._.is_empty(rooms) && !state.query ?
				h("div", { class: "vcenter" },
					h("div", { class: "text-center text-extra-muted" },
						h("p","",__("You don't have any messages yet."))
					)
				)
				:
				h(frappe.Chat.Widget.RoomList, { rooms: rooms, click: function (room) {
					if ( room.name )
						{ this$1.room.select(room.name); }
					else
						{ frappe.chat.room.create("Direct", room.owner, frappe._.squash(room.users), function (ref) {
							var name = ref.name;

							return this$1.room.select(name);
					}); }
				}});
			var Room       = h(frappe.Chat.Widget.Room, Object.assign({}, state.room, {layout: layout, destroy: function () {
				this$1.set_state({
					room: { name: null, messages: [ ] }
				});
			}}));

			var component  = layout === frappe.Chat.Layout.POPPER ?
				h(frappe.Chat.Widget.Popper, { heading: ActionBar, page: state.room.name && Room, target: props.target,
					toggle: function (t) { return this$1.set_state({ toggle: t }); } },
					RoomList
				)
				:
				h("div", { class: "frappe-chat-popper" },
					h("div", { class: "frappe-chat-popper-collapse" },
						h("div", { class: "panel panel-default panel-span", style: { width: "25%" } },
							h("div", { class: "panel-heading" },
								ActionBar
							),
							RoomList
						),
						Room
					)
				);

			return (
				h("div", { class: "frappe-chat" },
					component
				)
			)
		}
	};
	frappe.Chat.Widget.defaultState =  {
		  query: "",
		profile: { },
		  rooms: [ ],
		   room: { name: null, messages: [ ], typing: [ ] },
		 toggle: false,
		   span: false
	};
	frappe.Chat.Widget.defaultProps = {
		layout: frappe.Chat.Layout.POPPER
	};

	/**
	 * @description Chat Widget Popper HOC.
	 */
	frappe.Chat.Widget.Popper
	=
	class extends Component {
		constructor (props) {
			super (props);

			this.setup(props);
		}

		setup (props) {
			var this$1 = this;

			this.toggle = this.toggle.bind(this);

			this.state  = frappe.Chat.Widget.Popper.defaultState;

			if ( props.target )
				{ $(props.target).click(function () { return this$1.toggle(); }); }

			frappe.chat.widget = this;
		}

		toggle  (active) {
			var toggle;
			if ( arguments.length === 1 )
				{ toggle = active; }
			else
				{ toggle = this.state.active ? false : true; }

			this.set_state({ active: toggle });

			this.props.toggle(toggle);
		}

		on_mounted ( ) {
			var this$1 = this;

			$(document.body).on('click', '.page-container, .frappe-chat-close', function (ref) {
				var currentTarget = ref.currentTarget;

				this$1.toggle(false);
			});
		}

		render  ( )  {
			var this$1 = this;

			var ref = this;
			var props = ref.props;
			var state = ref.state;

			return !state.destroy ?
			(
				h("div", { class: "frappe-chat-popper", style: !props.target ? { "margin-bottom": "80px" } : null },
					!props.target ?
						h(frappe.components.FAB, {
							  class: "frappe-fab",
							   icon: state.active ? "fa fa-fw fa-times" : "font-heavy octicon octicon-comment",
							   size: frappe._.is_mobile() ? null : "large",
							   type: "primary",
							onclick: function () { return this$1.toggle(); },
						}) : null,
					state.active ?
						h("div", { class: "frappe-chat-popper-collapse" },
							props.page ? props.page : (
								h("div", { class: ("panel panel-default " + (frappe._.is_mobile() ? "panel-span" : "")) },
									h("div", { class: "panel-heading" },
										props.heading
									),
									props.children
								)
							)
					) : null
				)
			) : null
		}
	};
	frappe.Chat.Widget.Popper.defaultState
	=
	{
		 active: false,
		destroy: false
	};

	/**
	 * @description frappe.Chat.Widget ActionBar Component
	 */
	frappe.Chat.Widget.ActionBar
	=
	class extends Component {
		constructor (props) {
			super (props);

			this.change = this.change.bind(this);
			this.submit = this.submit.bind(this);

			this.state  = frappe.Chat.Widget.ActionBar.defaultState;
		}

		change (e) {
			var obj;

			var ref = this;
			var props = ref.props;
			var state = ref.state;

			this.set_state(( obj = {}, obj[e.target.name] = e.target.value, obj ));

			props.change(state.query);
		}

		submit (e) {
			var ref = this;
			var props = ref.props;
			var state = ref.state;

			e.preventDefault();

			props.submit(state.query);
		}

		render ( ) {
			var me               = this;
			var ref = this;
			var props = ref.props;
			var state = ref.state;
			var actions = props.actions;

			return (
				h("div", { class: ("frappe-chat-action-bar " + (props.class ? props.class : "")) },
					h("form", { oninput: this.change, onsubmit: this.submit },
						h("input", { autocomplete: "off", class: "form-control input-sm", name: "query", value: state.query, placeholder: props.placeholder || "Search" })
					),
					!frappe._.is_empty(actions) ?
						actions.map(function (action) { return h(frappe.Chat.Widget.ActionBar.Action, Object.assign({}, action)); }) : null,
					!frappe._.is_mobile() ?
						h(frappe.Chat.Widget.ActionBar.Action, {
							icon: ("octicon octicon-screen-" + (state.span ? "normal" : "full")),
							onclick: function () {
								var span = !state.span;
								me.set_state({ span: span });
								props.span(span);
							}
						})
						:
						null
				)
			)
		}
	};
	frappe.Chat.Widget.ActionBar.defaultState
	=
	{
		query: null,
		 span: false
	};

	/**
	 * @description frappe.Chat.Widget ActionBar's Action Component.
	 */
	frappe.Chat.Widget.ActionBar.Action
	=
	class extends Component {
		render ( ) {
			var ref = this;
			var props = ref.props;

			return (
				h(frappe.components.Button, Object.assign({}, {size: "small", class: "btn-action"}, props),
					props.icon ? h("i", { class: props.icon }) : null,
					("" + (props.icon ? " " : "") + (props.label ? props.label : ""))
				)
			)
		}
	};

	/**
	 * @description frappe.Chat.Widget RoomList Component
	 */
	frappe.Chat.Widget.RoomList
	=
	class extends Component {
		render ( ) {
			var ref = this;
			var props = ref.props;
			var rooms     = props.rooms;

			return !frappe._.is_empty(rooms) ? (
				h("ul", { class: "frappe-chat-room-list nav nav-pills nav-stacked" },
					rooms.map(function (room) { return h(frappe.Chat.Widget.RoomList.Item, Object.assign({}, room, {click: props.click})); })
				)
			) : null
		}
	};

	/**
	 * @description frappe.Chat.Widget RoomList's Item Component
	 */
	frappe.Chat.Widget.RoomList.Item
	=
	class extends Component {
		render ( ) {
			var ref    = this;
			var props = ref.props;
			var item         = { };

			if ( props.type === "Group" ) {
				item.title     = props.room_name;
				item.image     = props.avatar;

				if ( !frappe._.is_empty(props.typing) ) {
					props.typing  = frappe._.as_array(props.typing); // HACK: (BUG) why does typing return a string?
					var names   = props.typing.map(function (user) { return frappe.user.first_name(user); });
					item.subtitle = (names.join(", ")) + " typing...";
				} else
				if ( props.last_message ) {
					var message = props.last_message;
					var content = message.content;

					if ( message.type === "File" ) {
						item.subtitle = "📁 " + (content.name);
					} else {
						item.subtitle = props.last_message.content;
					}
				}
			} else {
				var user     = props.owner === frappe.session.user ? frappe._.squash(props.users) : props.owner;

				item.title     = frappe.user.full_name(user);
				item.image     = frappe.user.image(user);
				item.abbr      = frappe.user.abbr(user);

				if ( !frappe._.is_empty(props.typing) )
					{ item.subtitle = 'typing...'; }
				else
				if ( props.last_message ) {
					var message$1 = props.last_message;
					var content$1 = message$1.content;

					if ( message$1.type === "File" ) {
						item.subtitle = "📁 " + (content$1.name);
					} else {
						item.subtitle = props.last_message.content;
					}
				}
			}

			var is_unread = false;
			if ( props.last_message ) {
				item.timestamp = frappe.chat.pretty_datetime(props.last_message.creation);
				is_unread = !props.last_message.seen.includes(frappe.session.user);
			}

			return (
				h("li", null,
					h("a", { class: props.active ? "active": "", onclick: function () {
						if (props.last_message) {
							frappe.chat.message.seen(props.last_message.name);
						}
						props.click(props);
					} },
						h("div", { class: "row" },
							h("div", { class: "col-xs-9" },
								h(frappe.Chat.Widget.MediaProfile, Object.assign({}, item))
							),
							h("div", { class: "col-xs-3 text-right" },
								[
									h("div", { class: "text-muted", style: { "font-size": "9px" } }, item.timestamp),
									is_unread ? h("span", { class: "indicator red" }) : null
								]
							)
						)
					)
				)
			)
		}
	};

	/**
	 * @description frappe.Chat.Widget's MediProfile Component.
	 */
	frappe.Chat.Widget.MediaProfile
	=
	class extends Component {
		render ( ) {
			var ref = this;
			var props = ref.props;
			var position  = frappe.Chat.Widget.MediaProfile.POSITION[props.position || "left"];
			var avatar    = (
				h("div", { class: ((position.class) + " media-middle") },
					h(frappe.components.Avatar, Object.assign({}, props,
						{title: props.title,
						image: props.image,
						 size: props.size,
						 abbr: props.abbr}))
				)
			);

			return (
				h("div", { class: "media", style: position.class === "media-right" ? { "text-align": "right" } : null },
					position.class === "media-left"  ? avatar : null,
					h("div", { class: "media-body" },
						h("div", { class: "media-heading ellipsis small", style: ("max-width: " + (props.width_title || "100%") + " display: inline-block") }, props.title),
						props.content  ? h("div","",h("small","",props.content))  : null,
						props.subtitle ? h("div",{ class: "media-subtitle small" },h("small", { class: "text-muted" }, props.subtitle)) : null
					),
					position.class === "media-right" ? avatar : null
				)
			)
		}
	};
	frappe.Chat.Widget.MediaProfile.POSITION
	=
	{
		left: { class: "media-left" }, right: { class: "media-right" }
	};

	/**
	 * @description frappe.Chat.Widget Room Component
	 */
	frappe.Chat.Widget.Room
	=
	class extends Component {
		render ( ) {
			var ref = this;
			var props = ref.props;
			var state = ref.state;
			var hints            =
			[
				{
					 match: /@(\w*)$/,
					search: function (keyword, callback) {
						if ( props.type === 'Group' ) {
							var query = keyword.slice(1);
							var users = [].concat(frappe._.as_array(props.owner), props.users);
							var grep  = users.filter(function (user) { return user !== frappe.session.user && user.indexOf(query) === 0; });

							callback(grep);
						}
					},
					component: function (item) {
						return (
							h(frappe.Chat.Widget.MediaProfile, {
								title: frappe.user.full_name(item),
								image: frappe.user.image(item),
								 size: "small"
							})
						)
					}
				},
				{
					match: /:([a-z]*)$/,
				   search: function (keyword, callback) {
						frappe.chat.emoji(function (emojis) {
							var query = keyword.slice(1);
							var items = [ ];
							for (var i$1 = 0, list$1 = emojis; i$1 < list$1.length; i$1 += 1)
								{
								var emoji = list$1[i$1];

								for (var i = 0, list = emoji.aliases; i < list.length; i += 1)
									{
										var alias = list[i];

										if ( alias.indexOf(query) === 0 )
										{ items.push({ name: alias, value: emoji.emoji });
									}
							} }

							callback(items);
						});
				   },
					 content: function (item) { return item.value; },
				   component: function (item) {
						return (
							h(frappe.Chat.Widget.MediaProfile, {
								title: item.name,
								 abbr: item.value,
								 size: "small"
							})
						)
				   }
			   }
			];

			var actions = frappe._.compact([
				!frappe._.is_mobile() && {
					 icon: "camera",
					label: "Camera",
					onclick: function ( ) {
						var capture = new frappe.ui.Capture({
							animate: false,
							  error: true
						});
						capture.show();

						capture.submit(function (data_url) {
							// data_url
						});
					}
				},
				{
					 icon: "file",
					label: "File",
					onclick: function ( ) {
						new frappe.ui.FileUploader({
							doctype: "Chat Room",
							docname: props.name,
							on_success: function on_success(file_doc) {
								var file_url = file_doc.file_url;
								var filename = file_doc.filename;
								frappe.chat.message.send(props.name, { path: file_url, name: filename }, "File");
							}
						});
					}
				}
			]);

			if ( frappe.session.user !== 'Guest' ) {
				if (props.messages) {
					props.messages = frappe._.as_array(props.messages);
					for (var i = 0, list = props.messages; i < list.length; i += 1)
						{
						var message = list[i];

						if ( !message.seen.includes(frappe.session.user) )
							{ frappe.chat.message.seen(message.name); }
						else
							{ break
					} }
				}
			}

			return (
				h("div", { class: ("panel panel-default\n\t\t\t\t" + (props.name ? "panel-bg" : "") + "\n\t\t\t\t" + (props.layout === frappe.Chat.Layout.PAGE || frappe._.is_mobile() ? "panel-span" : "")),
					style: props.layout === frappe.Chat.Layout.PAGE && { width: "75%", left: "25%", "box-shadow": "none" } },
					props.name && h(frappe.Chat.Widget.Room.Header, Object.assign({}, props, {on_back: props.destroy})),
					props.name ?
						!frappe._.is_empty(props.messages) ?
							h(frappe.chat.component.ChatList, {
								messages: props.messages
							})
							:
							h("div", { class: "panel-body", style: { "height": "100%" } },
								h("div", { class: "vcenter" },
									h("div", { class: "text-center text-extra-muted" },
										h(frappe.components.Octicon, { type: "comment-discussion", style: "font-size: 48px" }),
										h("p","",__("Start a conversation."))
									)
								)
							)
						:
						h("div", { class: "panel-body", style: { "height": "100%" } },
							h("div", { class: "vcenter" },
								h("div", { class: "text-center text-extra-muted" },
									h(frappe.components.Octicon, { type: "comment-discussion", style: "font-size: 125px" }),
									h("p","",__("Select a chat to start messaging."))
								)
							)
						),
					props.name ?
						h("div", { class: "chat-room-footer" },
							h(frappe.chat.component.ChatForm, { actions: actions,
								onchange: function () {
									frappe.chat.message.typing(props.name);
								},
								onsubmit: function (message) {
									frappe.chat.message.send(props.name, message);
								},
								hint: hints
							})
						)
						:
						null
				)
			)
		}
	};

	frappe.Chat.Widget.Room.Header
	=
	class extends Component {
		render ( ) {
			var ref     = this;
			var props = ref.props;

			var item          = { };

			if ( ["Group", "Visitor"].includes(props.type) ) {
				item.route      = "Form/Chat Room/" + (props.name);

				item.title      = props.room_name;
				item.image      = props.avatar;

				if ( !frappe._.is_empty(props.typing) ) {
					props.typing  = frappe._.as_array(props.typing); // HACK: (BUG) why does typing return as a string?
					var users   = props.typing.map(function (user) { return frappe.user.first_name(user); });
					item.subtitle = (users.join(", ")) + " typing...";
				} else
					{ item.subtitle = props.type === "Group" ?
						__(((props.users.length) + " " + (frappe._.pluralize('member', props.users.length))))
						:
						""; }
			}
			else {
				var user      = props.owner === frappe.session.user ? frappe._.squash(props.users) : props.owner;

				item.route      = "Form/User/" + user;

				item.title      = frappe.user.full_name(user);
				item.image      = frappe.user.image(user);

				if ( !frappe._.is_empty(props.typing) )
					{ item.subtitle = 'typing...'; }
			}

			var popper        = props.layout === frappe.Chat.Layout.POPPER || frappe._.is_mobile();

			return (
				h("div", { class: "panel-heading", style: { "height": "50px" } }, // sorry. :(
					h("div", { class: "level" },
						popper && frappe.session.user !== "Guest" ?
							h(frappe.components.Button,{class:"btn-back",onclick:props.on_back},
								h(frappe.components.Octicon, { type: "chevron-left" })
							) : null,
						h("div","",
							h("div", { class: "panel-title" },
								h("div", { class: "cursor-pointer", onclick: function () { frappe.set_route(item.route); }},
									h(frappe.Chat.Widget.MediaProfile, Object.assign({}, item))
								)
							)
						),
						h("div", { class: popper ? "col-xs-1"  : "col-xs-3" },
							h("div", { class: "text-right" }

							)
						)
					)
				)
			)
		}
	};

	/**
	 * @description ChatList Component
	 *
	 * @prop {array} messages - ChatMessage(s)
	 */
	frappe.chat.component.ChatList
	=
	class extends Component {
		on_mounted ( ) {
			this.$element  = $('.frappe-chat').find('.chat-list');
			this.$element.scrollTop(this.$element[0].scrollHeight);
		}

		on_updated ( ) {
			this.$element.scrollTop(this.$element[0].scrollHeight);
		}

		render ( ) {
			var messages = [ ];
			for (var i   = 0 ; i < this.props.messages.length ; ++i) {
				var   message   = this.props.messages[i];
				var me        = message.user === frappe.session.user;

				if ( i === 0 || !frappe.datetime.equal(message.creation, this.props.messages[i - 1].creation, 'day') )
					{ messages.push({ type: "Notification", content: message.creation.format('MMMM DD') }); }

				messages.push(message);
			}

			return (
				h("div",{class:"chat-list list-group"},
					!frappe._.is_empty(messages) ?
						messages.map(function (m) { return h(frappe.chat.component.ChatList.Item, Object.assign({}, m)); }) : null
				)
			)
		}
	};

	/**
	 * @description ChatList.Item Component
	 *
	 * @prop {string} name       - ChatMessage name
	 * @prop {string} user       - ChatMessage user
	 * @prop {string} room       - ChatMessage room
	 * @prop {string} room_type  - ChatMessage room_type ("Direct", "Group" or "Visitor")
	 * @prop {string} content    - ChatMessage content
	 * @prop {frappe.datetime.datetime} creation - ChatMessage creation
	 *
	 * @prop {boolean} groupable - Whether the ChatMessage is groupable.
	 */
	frappe.chat.component.ChatList.Item
	=
	class extends Component {
		render ( ) {
			var ref = this;
			var props = ref.props;

			var me        = props.user === frappe.session.user;
			var content   = props.content;

			return (
				h("div",{class: "chat-list-item list-group-item"},
					props.type === "Notification" ?
						h("div",{class:"chat-list-notification"},
							h("div",{class:"chat-list-notification-content"},
								content
							)
						)
						:
						h("div",{class:("" + (me ? "text-right" : ""))},
							props.room_type === "Group" && !me ?
								h(frappe.components.Avatar, {
									title: frappe.user.full_name(props.user),
									image: frappe.user.image(props.user)
								}) : null,
							h(frappe.chat.component.ChatBubble, props)
						)
				)
			)
		}
	};

	/**
	 * @description ChatBubble Component
	 *
	 * @prop {string} name       - ChatMessage name
	 * @prop {string} user       - ChatMessage user
	 * @prop {string} room       - ChatMessage room
	 * @prop {string} room_type  - ChatMessage room_type ("Direct", "Group" or "Visitor")
	 * @prop {string} content    - ChatMessage content
	 * @prop {frappe.datetime.datetime} creation - ChatMessage creation
	 *
	 * @prop {boolean} groupable - Whether the ChatMessage is groupable.
	 */
	frappe.chat.component.ChatBubble
	=
	class extends Component {
		constructor (props) {
			super (props);

			this.onclick = this.onclick.bind(this);
		}

		onclick ( ) {
			var ref = this;
			var props = ref.props;
			if ( props.user === frappe.session.user ) {
				frappe.quick_edit("Chat Message", props.name, function (values) {

				});
			}
		}

		render  ( ) {
			var ref = this;
			var props = ref.props;
			var creation 	= props.creation.format('hh:mm A');

			var me        = props.user === frappe.session.user;
			var read      = !frappe._.is_empty(props.seen) && !props.seen.includes(frappe.session.user);

			var content   = props.content;

			return (
				h("div",{class:("chat-bubble " + (props.groupable ? "chat-groupable" : "") + " chat-bubble-" + (me ? "r" : "l")),
					onclick: this.onclick},
					props.room_type === "Group" && !me?
						h("div",{class:"chat-bubble-author"},
							h("a", { onclick: function () { frappe.set_route(("Form/User/" + (props.user))); } },
								frappe.user.full_name(props.user)
							)
						) : null,
					h("div",{class:"chat-bubble-content"},
							h("small","",
								props.type === "File" ?
									h("a", { class: "no-decoration", href: content.path, target: "_blank" },
										h(frappe.components.FontAwesome, { type: "file", fixed: true }), (" " + (content.name))
									)
									:
									content
							)
					),
					h("div",{class:"chat-bubble-meta"},
						h("span",{class:"chat-bubble-creation"},creation),
						me && read ?
							h("span",{class:"chat-bubble-check"},
								h(frappe.components.Octicon,{type:"check"})
							) : null
					)
				)
			)
		}
	};

	/**
	 * @description ChatForm Component
	 */
	frappe.chat.component.ChatForm
	=
	class extends Component {
		constructor (props) {
			super (props);

			this.onchange   = this.onchange.bind(this);
			this.onsubmit   = this.onsubmit.bind(this);

			this.hint        = this.hint.bind(this);

			this.state       = frappe.chat.component.ChatForm.defaultState;
		}

		onchange (e) {
			var obj;

			var ref = this;
			var props = ref.props;
			var state = ref.state;
			var value            = e.target.value;

			this.set_state(( obj = {}, obj[e.target.name] = value, obj ));

			props.onchange(state);

			this.hint(value);
		}

		hint (value) {
			var this$1 = this;

			var ref = this;
			var props = ref.props;
			var state = ref.state;

			if ( props.hint ) {
				var tokens =  value.split(" ");
				var sliced = tokens.slice(0, tokens.length - 1);

				var token  = tokens[tokens.length - 1];

				if ( token ) {
					props.hint   = frappe._.as_array(props.hint);
					var hint   = props.hint.find(function (hint) { return hint.match.test(token); });

					if ( hint ) {
						hint.search(token, function (items) {
							var hints = items.map(function (item) {
								// You should stop writing one-liners! >_>
								var replace = token.replace(hint.match, hint.content ? hint.content(item) : item);
								var content = ((sliced.join(" ")) + " " + replace).trim();
								item          = { component: hint.component(item), content: content };

								return item
							}).slice(0, hint.max || 5);

							this$1.set_state({ hints: hints });
						});
					}
					else
						{ this.set_state({ hints: [ ] }); }
				} else
					{ this.set_state({ hints: [ ] }); }
			}
		}

		onsubmit (e) {
			e.preventDefault();

			if ( this.state.content ) {
				this.props.onsubmit(this.state.content);

				this.set_state({ content: null });
			}
		}

		render ( ) {
			var this$1 = this;

			var ref = this;
			var props = ref.props;
			var state = ref.state;

			return (
				h("div",{class:"chat-form"},
					state.hints.length ?
						h("ul", { class: "hint-list list-group" },
							state.hints.map(function (item) {
								return (
									h("li", { class: "hint-list-item list-group-item" },
										h("a", { href: "javascript:void(0)", onclick: function () {
											this$1.set_state({ content: item.content, hints: [ ] });
										}},
											item.component
										)
									)
								)
							})
						) : null,
					h("form", { oninput: this.onchange, onsubmit: this.onsubmit },
						h("div",{class:"input-group input-group-lg"},
							!frappe._.is_empty(props.actions) ?
								h("div",{class:"input-group-btn dropup"},
									h(frappe.components.Button,{ class: "dropdown-toggle", "data-toggle": "dropdown"},
										h(frappe.components.FontAwesome, { class: "text-muted", type: "paperclip", fixed: true })
									),
									h("div",{ class:"dropdown-menu dropdown-menu-left", onclick: function (e) { return e.stopPropagation(); } },
										!frappe._.is_empty(props.actions) && props.actions.map(function (action) {
											return (
												h("li", null,
													h("a",{onclick:action.onclick},
														h(frappe.components.FontAwesome,{type:action.icon,fixed:true}), (" " + (action.label))
													)
												)
											)
										})
									)
								) : null,
							h("textarea", {
										class: "form-control",
										 name: "content",
										value: state.content,
								  placeholder: "Type a message",
									autofocus: true,
								   onkeypress: function (e) {
										if ( e.which === frappe.ui.keycode.RETURN && !e.shiftKey )
											{ this$1.onsubmit(e); }
								   }
							}),
							h("div",{class:"input-group-btn"},
								h(frappe.components.Button, { onclick: this.onsubmit },
									h(frappe.components.FontAwesome, { class: !frappe._.is_empty(state.content) ? "text-primary" : "text-muted", type: "send", fixed: true })
								)
							)
						)
					)
				)
			)
		}
	};
	frappe.chat.component.ChatForm.defaultState
	=
	{
		content: null,
		  hints: [ ],
	};

	/**
	 * @description EmojiPicker Component
	 *
	 * @todo Under Development
	 */
	frappe.chat.component.EmojiPicker
	=
	class extends Component  {
		render ( ) {
			var ref = this;
			var props = ref.props;

			return (
				h("div", { class: ("frappe-chat-emoji dropup " + (props.class)) },
					h(frappe.components.Button, { type: "primary", class: "dropdown-toggle", "data-toggle": "dropdown" },
						h(frappe.components.FontAwesome, { type: "smile-o", fixed: true })
					),
					h("div", { class: "dropdown-menu dropdown-menu-right", onclick: function (e) { return e.stopPropagation(); } },
						h("div", { class: "panel panel-default" },
							h(frappe.chat.component.EmojiPicker.List)
						)
					)
				)
			)
		}
	};
	frappe.chat.component.EmojiPicker.List
	=
	class extends Component {
		render ( ) {
			var ref = this;
			var props = ref.props;

			return (
				h("div", { class: "list-group" }

				)
			)
		}
	};

	/**
	 * @description Python equivalent to sys.platform
	 */
	frappe.provide('frappe._');
	frappe._.platform   = function () {
		var string    = navigator.appVersion;

		if ( string.includes("Win") ) 	{ return "Windows" }
		if ( string.includes("Mac") ) 	{ return "Darwin" }
		if ( string.includes("X11") ) 	{ return "UNIX" }
		if ( string.includes("Linux") ) { return "Linux" }

		return undefined
	};

	/**
	 * @description Frappe's Asset Helper
	 */
	frappe.provide('frappe.assets');
	frappe.assets.image = function (image, app) {
		if ( app === void 0 ) app = 'frappe';

		var  path     = "/assets/" + app + "/images/" + image;
		return path
	};

	/**
	 * @description Notify using Web Push Notifications
	 */
	frappe.provide('frappe.boot');
	frappe.provide('frappe.browser');
	frappe.browser.Notification = 'Notification' in window;

	frappe.notify     = function (string, options) {
		frappe.log    = frappe.Logger.get('frappe.notify');

		var OPTIONS = {
			icon: frappe.assets.image('favicon.png', 'frappe'),
			lang: frappe.boot.lang || "en"
		};
		options       = Object.assign({ }, OPTIONS, options);

		if ( !frappe.browser.Notification )
			{ frappe.log.error('ERROR: This browser does not support desktop notifications.'); }

		Notification.requestPermission(function (status) {
			if ( status === "granted" ) {
				var notification = new Notification(string, options);
			}
		});
	};

	frappe.chat.render = function (render, force) {
		if ( render === void 0 ) render = true;
		if ( force === void 0 ) force = false;

		frappe.log.info(((render ? "Enable" : "Disable") + " Chat for User."));

		var desk = 'desk' in frappe;
		if ( desk ) {
			// With the assumption, that there's only one navbar.
			var $placeholder = $('.navbar .frappe-chat-dropdown');

			// Render if frappe-chat-toggle doesn't exist.
			if ( frappe.utils.is_empty($placeholder.has('.frappe-chat-toggle')) ) {
				var $template = $("\n\t\t\t\t<a class=\"dropdown-toggle frappe-chat-toggle\" data-toggle=\"dropdown\">\n\t\t\t\t\t<div>\n\t\t\t\t\t\t<i class=\"octicon octicon-comment-discussion\"/>\n\t\t\t\t\t</div>\n\t\t\t\t</a>\n\t\t\t");

				$placeholder.addClass('dropdown hidden');
				$placeholder.html($template);
			}

			if ( render ) {
				$placeholder.removeClass('hidden');
			} else {
				$placeholder.addClass('hidden');
			}
		}

		// Avoid re-renders. Once is enough.
		if ( !frappe.chatter || force ) {
			frappe.chatter = new frappe.Chat({
				target: desk ? '.frappe-chat-toggle' : null
			});

			if ( render ) {
				if ( frappe.session.user === 'Guest' && !desk ) {
					frappe.store = frappe.Store.get('frappe.chat');
					var token	 = frappe.store.get('guest_token');

					frappe.log.info(("Local Guest Token - " + token));

					var setup_room = function (token) {
						return new Promise(function (resolve) {
							frappe.chat.room.create("Visitor", token).then(function (room) {
								frappe.log.info(("Visitor Room Created: " + (room.name)));
								frappe.chat.room.subscribe(room.name);

								var reference = room;

								frappe.chat.room.history(room.name).then(function (messages) {
									var  room = Object.assign({}, reference, {messages: messages});
									return room
								}).then(function (room) {
									resolve(room);
								});
							});
						})
					};

					if ( !token ) {
						frappe.chat.website.token().then(function (token) {
							frappe.log.info(("Generated Guest Token - " + token));
							frappe.store.set('guest_token', token);

							setup_room(token).then(function (room) {
								frappe.chatter.render({ room: room });
							});
						});
					} else {
						setup_room(token).then(function (room) {
							frappe.chatter.render({ room: room });
						});
					}
				} else {
					frappe.chatter.render();
				}
			}
		}
	};

	frappe.chat.setup  = function () {
		frappe.log     = frappe.Logger.get('frappe.chat');

		frappe.log.info('Setting up frappe.chat');
		frappe.log.warn('TODO: frappe.chat.<object> requires a storage.');

		if ( frappe.session.user !== 'Guest' ) {
			// Create/Get Chat Profile for session User, retrieve enable_chat
			frappe.log.info('Creating a Chat Profile.');

			frappe.chat.profile.create('enable_chat').then(function (ref) {
				var enable_chat = ref.enable_chat;

				frappe.log.info(("Chat Profile created for User " + (frappe.session.user) + "."));

				if ( 'desk' in frappe ) { // same as desk?
					var should_render = Boolean(parseInt(frappe.sys_defaults.enable_chat)) && enable_chat;
					frappe.chat.render(should_render);
				}
			});

			// Triggered when a User updates his/her Chat Profile.
			// Don't worry, enable_chat is broadcasted to this user only. No overhead. :)
			frappe.chat.profile.on.update(function (user, profile) {
				if ( user === frappe.session.user && 'enable_chat' in profile ) {
					frappe.log.warn(("Chat Profile update (Enable Chat - " + (Boolean(profile.enable_chat)) + ")"));
					var should_render = Boolean(parseInt(frappe.sys_defaults.enable_chat)) && profile.enable_chat;
					frappe.chat.render(should_render);
				}
			});
		} else {
			// Website Settings
			frappe.log.info('Retrieving Chat Website Settings.');
			frappe.chat.website.settings(["socketio", "enable", "enable_from", "enable_to"])
				.then(function (settings) {
					frappe.log.info(("Chat Website Setting - " + (JSON.stringify(settings))));
					frappe.log.info(("Chat Website Setting - " + (settings.enable ? "Enable" : "Disable")));

					var should_render = settings.enable;
					if ( settings.enable_from && settings.enable_to ) {
						frappe.log.info(("Enabling Chat Schedule - " + (settings.enable_from.format()) + " : " + (settings.enable_to.format())));

						var range   = new frappe.datetime.range(settings.enable_from, settings.enable_to);
						should_render = range.contains(frappe.datetime.now());
					}

					if ( should_render ) {
						frappe.log.info("Initializing Socket.IO");
						frappe.socketio.init(settings.socketio.port);
					}

					frappe.chat.render(should_render);
			});
		}
	};

	$(document).on('ready toolbar_setup', function () {
		frappe.chat.setup();
	});

}());
//# sourceMappingURL=chat.js.map
