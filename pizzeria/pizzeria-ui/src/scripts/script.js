/*
 * Copyright (C) 2016-2017  Jones Magloire @Joxit
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Sources inspired by https://joxit.github.io/docker-registry-ui/
 */
var pizzeria = {}
pizzeria.URL_QUERY_PARAM_REGEX = /[&?]url=/;
pizzeria.URL_PARAM_REGEX = /^url=/;

pizzeria.url = function() {
  if (!pizzeria._url) {
    var url = pizzeria.getUrlQueryParam();
    if (url) {
      try {
        pizzeria._url = pizzeria.decodeURI(url);
        return pizzeria._url;
      } catch (e) {
        console.log(e);
      }
    }
  }
  return pizzeria._url;
}

pizzeria.getUrlQueryParam = function () {
  var search = window.location.search;
  if (pizzeria.URL_QUERY_PARAM_REGEX.test(search)) {
    var param = search.split(/^\?|&/).find(function(param) {
      return param && pizzeria.URL_PARAM_REGEX.test(param);
    });
    return param ? param.replace(pizzeria.URL_PARAM_REGEX, '') : param;
  }
};

pizzeria.decodeURI = function(url) {
  return url.startsWith('http') ? window.decodeURIComponent(url) : atob(url);
};

pizzeria.catalog = {};

window.addEventListener('DOMContentLoaded', function() {
  riot.mount('*');
});
