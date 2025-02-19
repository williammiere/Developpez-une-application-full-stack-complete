export interface UpdateRequest {
    email: string;
    username: string;
    password: string | null;
    newPassword: string | null;
}