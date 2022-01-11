<template>
  <b-container>
    <b-row>
      <b-col cols="5" />
      <b-col cols="2">
        <b-button variant="success" v-b-modal.create-user-modal
          >Create</b-button
        >
      </b-col>
      <b-col cols="5" />
    </b-row>
    <b-row v-if="usersLoaded" class="py-4">
      <b-col cols="3"></b-col>
      <b-col>
        <b-table cols="6" v-if="usersLoaded" :fields="fields" :items="items" show-empty>
          <template #cell(name)="data">
            {{ data.item.login }}
          </template>
          <template #cell(details)="row">
            <b-button variant="secondary" @click="viewUser(row)">{{
              row.item._showDetails ? "Hide" : "Show"
            }}</b-button>
          </template>
          <template #cell(edit)="data">
            <b-button
              variant="warning"
              @click="openEditUserForm(data.item.login)"
              >Edit</b-button
            >
          </template>
          <template #cell(delete)="data">
            <b-button variant="danger" @click="deleteUser(data.item.login)">
              Delete
            </b-button>
          </template>
          <template #empty="">
            <h4><b>No users found</b></h4>
          </template>
          <template #row-details="row">
            <b-container v-if="row.item.detailsLoaded">
              <b-row class="mb-2">
                <b-col sm="3" class="text-sm-right"><b>Name:</b></b-col>
                <b-col cols="3">{{ row.item.name }}</b-col>
                <b-col sm="3" class="text-sm-right"><b>Surname:</b></b-col>
                <b-col cols="3">{{ row.item.surname }}</b-col>
              </b-row>
              <b-row class="mb-2">
                <b-col sm="3" class="text-sm-right"><b>Email:</b></b-col>
                <b-col cols="3">{{ row.item.email }}</b-col>
              </b-row>
              <b-row class="mb-2">
                <Cars :detailed="true" :user="row.item.login" />
              </b-row>
            </b-container>
            <b-container v-else>
              <b-col cols="12" class="py-4">
                <b-spinner style="width: 3rem; height: 3rem" />
              </b-col>
            </b-container>
          </template>
        </b-table>
      </b-col>
      <b-col cols="3"></b-col>
    </b-row>
    <b-row v-else>
      <b-col cols="12" class="py-4">
        <b-spinner style="width: 3rem; height: 3rem" />
      </b-col>
    </b-row>
    <b-modal id="create-user-modal" size="lg" :hide-header="true">
      <b-container>
        <h4>Create new user form</h4>
        <hr />
        <b-row class="pt-2">
          <b-col cols="3">
            <b>Login</b>
          </b-col>
          <b-col cols="3">
            <b-form-input v-model="form.login" placeholder="Login" />
          </b-col>
          <b-col cols="3">
            <b>Password</b>
          </b-col>
          <b-col cols="3">
            <b-form-input
              v-model="form.password"
              :type="'password'"
              placeholder="Password"
            />
          </b-col>
        </b-row>
        <b-row class="pt-4">
          <b-col cols="3">
            <b>Name</b>
          </b-col>
          <b-col cols="3">
            <b-form-input v-model="form.name" placeholder="Name" />
          </b-col>
          <b-col cols="3">
            <b>Surname</b>
          </b-col>
          <b-col cols="3">
            <b-form-input v-model="form.surname" placeholder="Surname" />
          </b-col>
        </b-row>
        <b-row class="pt-4">
          <b-col cols="3">
            <b>Email</b>
          </b-col>
          <b-col cols="3">
            <b-form-input
              v-model="form.email"
              :type="'email'"
              placeholder="Email"
            />
          </b-col>
          <b-col cols="3">
            <b>Birthdate</b>
          </b-col>
          <b-col cols="3">
            <b-form-input
              v-model="form.birthdate"
              :type="'date'"
              placeholder="YYYY-MM-DD"
            />
          </b-col>
        </b-row>
      </b-container>
      <template #modal-footer="{}">
        <b-button variant="danger" @click="closeCreateUserForm()">
          Cancel
        </b-button>
        <b-button
          variant="success"
          @click="createUser()"
          :disabled="!validForm()"
          >Create user</b-button
        >
      </template>
    </b-modal>
    <b-modal id="edit-user-modal" size="lg" :hide-header="true">
      <b-container>
        <h4>Edit user form</h4>
        <hr />
        <b-row class="py-4">
          <b-col cols="3">
            <b>Name</b>
          </b-col>
          <b-col cols="3">
            <b-form-input v-model="userToEdit.name" placeholder="Name" />
          </b-col>
          <b-col cols="3">
            <b>Surname</b>
          </b-col>
          <b-col cols="3">
            <b-form-input v-model="userToEdit.surname" placeholder="Surname" />
          </b-col>
        </b-row>
        <b-row class="py-3">
          <b-col cols="3">
            <b>Email</b>
          </b-col>
          <b-col cols="6">
            <b-form-input
              v-model="userToEdit.email"
              :type="'email'"
              placeholder="Email"
            />
          </b-col>
        </b-row>
      </b-container>
      <template #modal-footer="{}">
        <b-button variant="danger" @click="closeEditUserForm()">
          Cancel
        </b-button>
        <b-button variant="success" @click="editUser()">Update user</b-button>
      </template>
    </b-modal>
  </b-container>
</template>

<script>
import {
  getUsers,
  getUser,
  createUser as apiCreateUser,
  deleteUser as apiDeleteUser,
  editUser as apiEditUser,
} from "@/api/api.js";
import Cars from "@/components/Cars.vue";

const FIELDS = ["login", "details", "edit", "delete"];

export default {
  name: "Users",
  components: {
    Cars,
  },
  data: function () {
    return {
      users: [],
      items: [],
      usersDetailed: {},
      fields: FIELDS,
      usersLoaded: false,
      form: {
        name: null,
        surname: null,
        login: null,
        password: null,
        email: null,
        birthdate: null,
      },
      userToEdit: {
        name: null,
        surname: null,
        email: null,
        birthdate: null,
      },
      editFormCorrect: false,
    };
  },

  async mounted() {
    await this.parseUsers();
  },

  methods: {
    async loadUsers() {
      let res = await getUsers();
      this.users = res.users;
    },

    async parseUsers() {
      this.usersLoaded = false;
      await this.loadUsers();
      this.items = [];

      for (let user of this.users) {
        user.detailsLoaded = false;
        this.items.push(JSON.parse(JSON.stringify(user)));
      }

      this.usersLoaded = true;
    },

    async viewUser(row) {
      await this.loadUser(row.item);
      row.toggleDetails();
    },

    async loadUser(userObject) {
      let { name, surname, email, birthdate } = await getUser(userObject.login);

      userObject.name = name;
      userObject.surname = surname;
      userObject.email = email;
      userObject.birthdate = birthdate;
      userObject.detailsLoaded = true;
    },

    async editUser() {
      await apiEditUser(this.userToEdit);
      this.resetEditForm();
      this.parseUsers();
      this.closeEditUserForm();
    },

    async findUserToEdit(login) {
      for (const user of this.items) {
        if (user.login == login) {
          await this.loadUser(user);
          console.log(user);
          this.userToEdit = user;
          break;
        }
      }
    },

    async deleteUser(login) {
      await apiDeleteUser(login);
      this.parseUsers();
    },

    resetCreateForm() {
      for (let key in this.form) {
        this.form[key] = null;
      }
    },

    resetEditForm() {
      for (let key in this.userToEdit) {
        this.form[key] = null;
      }
    },

    validForm() {
      for (let key in this.form) {
        if (this.form[key] == null) {
          return false;
        }
      }
      return true;
    },

    async createUser() {
      console.log(this.form);
      await apiCreateUser(this.form);
      this.resetCreateForm();
      this.parseUsers();
      this.closeCreateUserForm();
    },

    closeCreateUserForm() {
      this.$bvModal.hide("create-user-modal");
    },

    openEditUserForm(login) {
      this.findUserToEdit(login);
      this.$bvModal.show("edit-user-modal");
    },

    closeEditUserForm() {
      this.$bvModal.hide("edit-user-modal");
    },
  },
};
</script>

<style lang="scss" scoped></style>
