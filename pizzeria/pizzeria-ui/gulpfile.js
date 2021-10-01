const cleanCSS = require('gulp-clean-css');
const concat = require('gulp-concat');
const del = require('del');
const filter = require('gulp-filter');
const fs = require('fs');
const gIf = require('gulp-if');
const gulp = require('gulp');
const parallel = gulp.parallel;
const series = gulp.series;
const htmlmin = require('gulp-htmlmin');
const riot = require('gulp-riot');
const uglify = require('uglify-es');
const minifier = require('gulp-uglify/composer')(uglify);
const useref = require('gulp-useref');
const merge = require('stream-series');

function html() {
  const htmlFilter = filter('**/*.html', {restore: true});
  return gulp.src(['src/index.html'])
    .pipe(useref())
    .pipe(gIf(['*.js', '!*.min.js'], minifier({}, uglify))) // FIXME
    .pipe(htmlFilter)
    .pipe(htmlmin({
      removeComments: false,
      collapseWhitespace: true,
      removeRedundantAttributes: true,
      removeEmptyAttributes: true,
      minifyJS: uglify
    }))
    .pipe(htmlFilter.restore)
    .pipe(gulp.dest('dist'));
};

function clean() {
  return del('dist');
};

function app() {
  return merge(gulp.src('src/scripts/*.js'), gulp.src('src/tags/*.tag').pipe(riot()))
    .pipe(concat('script.js'))
    .pipe(minifier({}, uglify))
    .pipe(gulp.dest('dist/scripts'));
};

function vendor() {
  return gulp.src(['node_modules/riot/riot.min.js', 'node_modules/riotgear-router/dist/rg-router.min.js', 'node_modules/riot-mui/build/js/riot-mui-min.js'])
    .pipe(concat('vendor.js'))
    .pipe(gulp.dest('dist/scripts'));
};

function styles() {
  return gulp.src(['src/*.css'])
    .pipe(concat('style.css'))
    .pipe(cleanCSS({
      compatibility: 'ie8'
    }))
    .pipe(gulp.dest('dist/'));
};

exports.build = series(clean, html, parallel(styles, vendor, app));