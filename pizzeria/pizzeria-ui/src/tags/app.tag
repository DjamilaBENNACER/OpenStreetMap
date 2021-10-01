<!--
 Copyright (C) 2016-2017  Jones Magloire @Joxit

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
-->
<app>
  <header>
    <material-navbar>
      <div class="logo">Pizzeria</div>
    </material-navbar>
  </header>
  <main>
    <catalog></catalog>
    <material-snackbar></material-snackbar>
  </main>
  <footer>
    <material-footer>
      <a class="material-footer-logo" href="https://joxit.dev/IG-Master2/ui/">Pizzeria UI</a>
      <ul class="material-footer-link-list">
        <li>
          <a href="https://github.com/Joxit/IG-Master2">Projet sur GitHub</a>
        </li>
        <li>
          <a href="https://github.com/Joxit/IG-Master2/tree/master/beamer">Cours pour l'Institut Galilée</a>
        </li>
      </ul>

    </material-footer>
  </footer>
  <script>

    this.mixin('rg.router');
    this.router.add({name: 'home', url: ''});
    this.router.on('go', state => {
      switch (state.name) {
        case 'home':
          if (pizzeria.catalog.display) {
            pizzeria.catalog.loadend = false;
            pizzeria.catalog.display();
          }
          break;
      }
    });
    pizzeria.appTag = this;
    pizzeria.snackbar = function (message, isError) {
      pizzeria.appTag.tags['material-snackbar'].addToast({'message': message, 'isError': isError});
    };
    pizzeria.errorSnackbar = function (message) {
      return pizzeria.snackbar(message, true);
    }
    this.router.start();
  </script>
</app>